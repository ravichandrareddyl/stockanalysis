package com.stocks.analysis.config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.messaging.Message;

import com.stocks.analysis.constants.AppConstants;
import com.stocks.analysis.utils.AppUtil;

public class FileMessageToJobLaunchRequest {

	private Job job;

    public void setJob(Job job) {
        this.job = job;
    }

    public JobLaunchRequest toRequest(Message<File> message) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        
        String fileName = message.getPayload().getName();
        
        jobParametersBuilder.addString(AppConstants.JOB_FILE_NAME_PARAM, message.getPayload().getAbsolutePath());
        jobParametersBuilder.addString(AppConstants.JOB_FILE_SOURCE_PARAM, AppUtil.getSourceName(fileName));
        jobParametersBuilder.addDate(AppConstants.JOB_AS_ON_DATE, AppUtil.getAsOnDate(fileName));
        //comment below line while running in production
        //jobParametersBuilder.addLong("currentTime", new Long(System.currentTimeMillis()));
        return new JobLaunchRequest(this.job, jobParametersBuilder.toJobParameters());
    }
}