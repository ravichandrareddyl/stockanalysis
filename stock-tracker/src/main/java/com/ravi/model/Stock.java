package com.ravi.model;

import java.math.BigDecimal;

public class Stock {
	
	private String stock;
	private int stockId;
	private String  operation;
	private BigDecimal price;
	private String trackingStatus;
	private String alertStatus;
	private double percent;

	/**
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}
	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * @return the percent
	 */
	public double getPercent() {
		return percent;
	}
	/**
	 * @param percent the percent to set
	 */
	public void setPercent(double percent) {
		this.percent = percent;
	}
	/**
	 * @return the alertStatus
	 */
	public String getAlertStatus() {
		return alertStatus;
	}
	/**
	 * @param alertStatus the alertStatus to set
	 */
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}
	/**
	 * @return the trackingStatus
	 */
	public String getTrackingStatus() {
		return trackingStatus;
	}
	/**
	 * @param trackingStatus the trackingStatus to set
	 */
	public void setTrackingStatus(String trackingStatus) {
		this.trackingStatus = trackingStatus;
	}
	/**
	 * @return the stockId
	 */
	public int getStockId() {
		return stockId;
	}
	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}
}