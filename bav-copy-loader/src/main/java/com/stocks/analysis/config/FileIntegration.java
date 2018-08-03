package com.stocks.analysis.config;

import java.io.File;
import java.util.Arrays;

import com.stocks.analysis.constants.AppConstants;
import com.stocks.analysis.utils.AppUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.RegexPatternFileListFilter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;

@Configuration
@EnableIntegration
public class FileIntegration {

    private static final Logger log = LoggerFactory.getLogger(FileIntegration.class);
    
    @Autowired
    @Qualifier("bavJob")
    private Job bavJob;

    @Autowired
    @Qualifier("oldBavJob")
    private Job oldBavJob;
    
    @Autowired
    private JobLauncher jobLauncher;
    

    @Bean(name=PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedDelay(5000).errorChannel(AppConstants.ERROR_CHANNEL);
    }

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows
                .from(Files.inboundAdapter(new File(AppConstants.LOCAL_FOLDER))
                        .filter(new CompositeFileListFilter<File>(
                        		Arrays.asList(
                        				new RegexPatternFileListFilter(".*\\.txt|.*\\.csv")
                        				//,
                        				//new AcceptOnceFileListFilter<>()
                                ))),
                        c -> c.poller(Pollers.fixedDelay(AppConstants.LOCAL_POLLING_DURATION).maxMessagesPerPoll(3)))
                .channel(AppConstants.ROUTING_CHANNEL)
                .get();
    }

    @Bean
    public IntegrationFlow routerFlow() {
        return IntegrationFlows.from(AppConstants.ROUTING_CHANNEL)
                .<File, String>route(AppUtil.determineChannels())
                .get();
    }

    @Bean
    public IntegrationFlow oldBavFlow() {
        return IntegrationFlows.from(AppConstants.OLD_BAV_CHANNEL)
                .transform(fileMessageToJobLaunchRequest(this.oldBavJob))
                .handle(jobLaunchingGateway())
                .channel(AppConstants.JOB_LISTENER_CHANNEL)
                .handle(handler())
                .get();
    }

    @Bean
    public IntegrationFlow bavFlow() {
        return IntegrationFlows.from(AppConstants.BAV_CHANNEL)
                .transform(fileMessageToJobLaunchRequest(this.bavJob))
                .handle(jobLaunchingGateway())
                .channel(AppConstants.JOB_LISTENER_CHANNEL)
                .handle(handler())
                .get();
    }
    

    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                Assert.notNull(message, "Message object should not be null");
              
                final Object payload = message.getPayload();  
                Assert.isInstanceOf(JobExecution.class, payload, "The payload must be of type JobExecution.");
                final JobExecution jobExecution = (JobExecution) payload;

                log.info("Job details {}", jobExecution);
                
                if (jobExecution.getStatus().isUnsuccessful()) {
                    String fileName = jobExecution.getJobParameters().getString(AppConstants.JOB_FILE_NAME_PARAM);
                    AppUtil.renameFile(fileName, AppConstants.JOB_ERROR);
                }
            }

        };
    }
    
    FileMessageToJobLaunchRequest fileMessageToJobLaunchRequest(Job job) {
        FileMessageToJobLaunchRequest transformer = new FileMessageToJobLaunchRequest();
        transformer.setJob(job);
        return transformer;
    }
    
    JobLaunchingGateway jobLaunchingGateway() {
        JobLaunchingGateway jobLaunchingGateway = new JobLaunchingGateway(jobLauncher);
        return jobLaunchingGateway;
    }
}