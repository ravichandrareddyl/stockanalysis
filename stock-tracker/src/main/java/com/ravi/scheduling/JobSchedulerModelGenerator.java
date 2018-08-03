package com.ravi.scheduling;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravi.jobs.JobRunner;
import com.ravi.model.JobScheduleModel;
import com.ravi.model.Stock;
import com.ravi.model.StocksModel;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobSchedulerModelGenerator {

    Logger logger = LoggerFactory.getLogger(JobSchedulerModelGenerator.class);

    @Value("${configFile}")
    private String configLocation;

    public StocksModel getStockConfig() {
        StocksModel stocks = null;
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            stocks = mapper.readValue(new File(configLocation), StocksModel.class);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stocks;
    }

    public List<JobScheduleModel> generateModels() {
        StocksModel stocks = this.getStockConfig();
        List<JobScheduleModel> generatedModels = new ArrayList<JobScheduleModel>();
        for (Stock stock: stocks.getStocks()) {
            JobScheduleModel model = generateModelFrom(stock);
            generatedModels.add(model);
        }
        return generatedModels;
    }

    private JobScheduleModel generateModelFrom(Stock stock) {
        JobDetail jobDetail = getJobDetailFor(stock);
 
        Trigger trigger = getTriggerFor(stock.getCronExpression(), jobDetail);
        JobScheduleModel jobScheduleModel = new JobScheduleModel(jobDetail, trigger);
        return jobScheduleModel;
    }

    private JobDetail getJobDetailFor(Stock stock) {
        JobDetail jobDetail = JobBuilder.newJob(JobRunner.class)
                .setJobData(getJobDataMapFrom(stock))
                .withDescription("Job with report name : " + stock.getStock() +
                        " and CRON expression : " + stock.getCronExpression())
                .withIdentity(stock.getStock(), "STOCKS")
                .build();
        return jobDetail;
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
        jobDataMap.put("report", getStockConfigAsString(stock));
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