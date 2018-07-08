package com.stocks.analysis.jobs;

import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stocks.analysis.listeners.JobCompletionNotificationListener;

@Configuration
public class CommonJobConfig {
	
	@Autowired
	private Tasklet renameFileTaskLet;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Bean
    Step renameFile() {
        return stepBuilderFactory.get("deleteFile").tasklet(renameFileTaskLet).build();
    }
	
	@Bean
	JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}
	
	


}
