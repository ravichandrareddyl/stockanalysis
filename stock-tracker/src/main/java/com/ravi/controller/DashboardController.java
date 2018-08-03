// package com.ravi.controller;

// import com.ravi.model.ResponseModel;
// import com.ravi.service.DBRepo;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.ResponseBody;

// @Controller
// public class DashboardController {

// 	@Autowired
// 	private DBRepo dbRepo;

// 	@PostMapping("/getStatus/{country}")
// 	@ResponseBody
// 	public ResponseModel getStatus(@PathVariable String country) {
// 		long start = System.currentTimeMillis();
	
// 		return dbRepo.getdetails(country);
// 	}
// }
