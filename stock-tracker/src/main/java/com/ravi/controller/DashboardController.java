package com.ravi.controller;

import java.util.List;

import com.ravi.model.Stock;
import com.ravi.service.DBRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {

	@Autowired
	private DBRepo dbRepo;

	@PostMapping("/getStatus")
	@ResponseBody
	public List<Stock> getStatus() {
		return dbRepo.getAllStocks();
	}
}
