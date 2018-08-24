package com.ravi.jobs;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravi.model.Stock;
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
public class JobRunner implements Job {
    
    Logger logger = LoggerFactory.getLogger(JobRunner.class);

    private Stock stock;

    @Autowired
    private ProcessorService stockService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        stockService.process(this.stock);
    }
 
    public void setStock(String stockJson) {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            this.stock = mapper.readValue(stockJson, Stock.class);
        } catch (Exception e) {
            logger.error("failed to parse report json with error {}", e.getMessage());
        }
    }
       

    

}