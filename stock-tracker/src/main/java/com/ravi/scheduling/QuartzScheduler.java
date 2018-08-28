package com.ravi.scheduling;

import java.util.List;

import javax.annotation.PostConstruct;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import com.ravi.constants.AppConstants;
import com.ravi.jobs.JobCreator;
import com.ravi.model.JobScheduleModel;
import com.ravi.service.DBRepo;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzScheduler {

    Logger logger = LoggerFactory.getLogger(QuartzScheduler.class);

    @Autowired
    private DBRepo dao;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    @Autowired
    private JobSchedulerModelGenerator jobSchedulerModelGenerator;

    @PostConstruct
    public void init() {
        logger.info("Inside init method for initializing job");
        this.scheduleJobs(AppConstants.START_UP);
    }

    public void scheduleJobs(String mode) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobScheduleModel> jobScheduleModels = null;

        if (mode.equalsIgnoreCase(AppConstants.START_UP)) {
            jobScheduleModels = jobSchedulerModelGenerator.getStocksToBeTrackedAtStartup();
        } else {
            jobScheduleModels = jobSchedulerModelGenerator.getStocksToBeTrackedAtRunTime();
        }
        
        
        for (JobScheduleModel model : jobScheduleModels) {
            try {
                scheduler.scheduleJob(model.getJobDetail(), model.getTrigger());
            } catch (SchedulerException e) {
                logger.error("Failed to schedule jobs because of error {}", e.getMessage());
            }
        }
        
        
        try {
            if (mode.equalsIgnoreCase(AppConstants.START_UP)) {
                JobDetail runTimeExecutor = this.forkRunTimeExecutor();
                Trigger trigger = this.getTriggerFor(runTimeExecutor);
                scheduler.scheduleJob(runTimeExecutor, trigger);
            }
            scheduler.start();
            dao.updateTrackingStatus();
        } catch (SchedulerException e) {
            logger.error("Failed to schedule jobs because of error {}", e.getMessage());
        }
    }


    private JobDetail forkRunTimeExecutor() {
        JobDetail jobDetail = JobBuilder.newJob(JobCreator.class)
                .withDescription("Job creator job provisioned")
                .withIdentity(AppConstants.JOB_CREATOR, AppConstants.JOB_CREATOR_GROUP)
                .build();
        return jobDetail;
    }

    private Trigger getTriggerFor(JobDetail jobDetail) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(cronSchedule(AppConstants.RUN_TIME_EXECUTOR_CRON))
                .build();
        return trigger;
    }

}