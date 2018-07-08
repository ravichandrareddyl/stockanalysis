package com.stocks.analysis.jobs;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.stocks.analysis.constants.AppConstants;
import com.stocks.analysis.utils.AppUtil;

@Component
public class CustomTasks {

    @Bean
    public Tasklet renameFileTaskLet() {
        return (contribution, chunkContext) -> {
            String resource = (String) chunkContext.getStepContext().getJobParameters().get("input.file.name");
            AppUtil.renameFile(resource, AppConstants.JOB_PROCESSED);
            return RepeatStatus.FINISHED;
        };
    }


}