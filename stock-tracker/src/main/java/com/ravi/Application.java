package com.ravi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class Application {

	
	// @Autowired
	// private JobParser jobParser;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// 	ReportsModel reports = jobParser.getReportConfig();
	// 	System.out.println("printing reports" + reports.getReports().size());
	// }
}
