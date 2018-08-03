package com.ravi.scheduling;

import java.util.List;

import javax.annotation.PostConstruct;

import com.ravi.model.JobScheduleModel;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class QuartzScheduler {

    Logger logger = LoggerFactory.getLogger(QuartzScheduler.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    
    @Autowired
    private JobSchedulerModelGenerator jobSchedulerModelGenerator;

    @PostConstruct
    public void init() {
        logger.info("Inside init method for initializing job");
        this.scheduleJobs();
    }

    public void scheduleJobs() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<JobScheduleModel> jobScheduleModels = jobSchedulerModelGenerator.generateModels();
        for (JobScheduleModel model : jobScheduleModels) {
            try {
                logger.info("iside for loop");
                scheduler.scheduleJob(model.getJobDetail(), model.getTrigger());
            } catch (SchedulerException e) {
                logger.error("Failed to schedule jobs because of error {}", e.getMessage());
            }
        }
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("Failed to schedule jobs because of error {}", e.getMessage());
        }
    }

}