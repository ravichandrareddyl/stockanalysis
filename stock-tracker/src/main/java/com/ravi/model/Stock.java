package com.ravi.model;

public class Stock {
	
	private String stock;
	private String  operation;
	private float price;
	private String cronExpression;
	/**
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}
	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
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