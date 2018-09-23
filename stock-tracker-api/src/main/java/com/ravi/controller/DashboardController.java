package com.ravi.controller;

import java.util.List;

import javax.validation.Valid;

import com.ravi.domain.Stock;
import com.ravi.payload.ApiResponse;
import com.ravi.payload.StockRequest;
import com.ravi.repository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class DashboardController {

	@Autowired
	private StockRepository stockRepository;

	@GetMapping("/all")
	public List<Stock> getStatus() {
		return stockRepository.findAll();
	}

	@PostMapping("/save")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> saveStock(@Valid @RequestBody StockRequest stockRequest) {

		Stock stock = new Stock(stockRequest.getName(), stockRequest.getOperation(), stockRequest.getPrice(), stockRequest.getPercent());
		stockRepository.save(stock);
		return ResponseEntity.ok(new ApiResponse(true, "Stock saved for tracking"));
	}
}
