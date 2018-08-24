package com.ravi.scheduling;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravi.constants.AppConstants;
import com.ravi.jobs.JobRunner;
import com.ravi.model.JobScheduleModel;
import com.ravi.model.Stock;
import com.ravi.model.StocksModel;
import com.ravi.service.DBRepo;
import com.ravi.util.AppUtil;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JobSchedulerModelGenerator {

    Logger logger = LoggerFactory.getLogger(JobSchedulerModelGenerator.class);

    @Autowired
    private DBRepo dao;
    
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Value("${configFile}")
    private String configLocation;

    public List<Stock> getStocksToBeTracked() {
        return dao.getStocksToBeTracked();
    }

    public List<JobScheduleModel> generateModels() {
        List<Stock> stocks = this.getStocksToBeTracked();
        List<JobScheduleModel> generatedModels = new ArrayList<JobScheduleModel>();
        for (Stock stock: stocks) {
            JobScheduleModel model = generateModelFrom(stock);
            generatedModels.add(model);
        }
        return generatedModels;
    }

    private JobScheduleModel generateModelFrom(Stock stock) {
        JobDetail jobDetail = getJobDetailFor(stock);
        
        String cronExpression = AppUtil.getCronExpression();
        logger.info("STOCK:{} and cron Expression: {}", stock.getStock(), cronExpression);
        Trigger trigger = getTriggerFor(cronExpression, jobDetail);
        JobScheduleModel jobScheduleModel = new JobScheduleModel(jobDetail, trigger);
        return jobScheduleModel;
    }

    private JobDetail getJobDetailFor(Stock stock) {
        JobDetail jobDetail = JobBuilder.newJob(JobRunner.class)
                .setJobData(getJobDataMapFrom(stock))
                .withDescription("Job with report name : " + stock.getStock())
                .withIdentity(stock.getStock(), AppConstants.JOB_GROUP_NAME)
                .build();
        return jobDetail;
    }

    public void deleteJob(Stock stock) {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
			scheduler.deleteJob(new JobKey(stock.getStock(), AppConstants.JOB_GROUP_NAME));
            this.stopTracking(stock);
        } catch (SchedulerException e) {
			logger.error("Failed to delete job with error: {}", e.getMessage());
		}
    }

    private void stopTracking(Stock stock) {
        dao.stopTracking(stock);
    }

    public String getStockConfigAsString(Stock stock) {
        String reportConfig = null;
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            reportConfig = mapper.writeValueAsString(stock);
        } catch (Exception e) {
            logger.error("failed to covert stock object to string with error {}", e.getMessage());
        }

        return reportConfig;
        
    }

    private JobDataMap getJobDataMapFrom(Stock stock) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("stock", getStockConfigAsString(stock));
        return jobDataMap;
    }
 

    private Trigger getTriggerFor(String cronExpression, JobDetail jobDetail) {
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withSchedule(cronSchedule(cronExpression))
                .build();
        return trigger;
    }

}