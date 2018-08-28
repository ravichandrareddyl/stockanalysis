package com.ravi.jobs;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravi.constants.AppConstants;
import com.ravi.model.Stock;
import com.ravi.scheduling.QuartzScheduler;
import com.ravi.service.ProcessorService;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class JobCreator implements Job {
    
    Logger logger = LoggerFactory.getLogger(JobCreator.class);

    @Autowired
    private QuartzScheduler schedular;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        schedular.scheduleJobs(AppConstants.RUNTIME);
    }
}