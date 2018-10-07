package com.ravi.controller;

import java.util.List;

import com.ravi.constants.AppConstants;
import com.ravi.model.Stock;
import com.ravi.scheduling.QuartzScheduler;
import com.ravi.service.DBRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

	Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private DBRepo dbRepo;

	@Autowired
    private QuartzScheduler schedular;


	@PostMapping("/getStatus")
	@ResponseBody
	public List<Stock> getStatus() {
		return dbRepo.getAllStocks();
	}

	@PostMapping("/trackOrder")
	public ResponseEntity<String> trackOrder() {
		logger.info("starting runtime jobs");
		schedular.scheduleJobs(AppConstants.RUNTIME);
		return ResponseEntity.ok("started tracking jobs");
	}
}
