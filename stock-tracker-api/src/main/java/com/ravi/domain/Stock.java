package com.ravi.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "STOCKS_MASTER", uniqueConstraints = { 
	@UniqueConstraint(columnNames = { "id" })
})
public class Stock {
	
	private String name;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String  opertion;
	private BigDecimal price;
	
	@Column(name="tracking_status")
	private String trackingStatus;

	@Column(name="alert_status")
	private String alertStatus;
	
	private double percent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stockId")
	private List<StockHistory> history;

	public Stock() {}

	public Stock(String name, String operation, String price, String percent) {
		this.name = name;
		this.opertion = operation;
		this.price = new BigDecimal(price);
		this.percent = Double.parseDouble(percent);
	}

	//private BigDecimal marketPrice;

	/**
	 * @return the stock
	 */
	public String getStock() {
		return name;
	}

	/**
	 * @return the history
	 */
	public List<StockHistory> getHistory() {
		return history;
	}

	/**
	 * @param history the history to set
	 */
	public void setHistory(List<StockHistory> history) {
		if (null != history) {
			this.history = history;
		} else {
			this.history = new ArrayList<StockHistory>();
		}
		
	}

	/**
	 * @return the opertion
	 */
	public String getOpertion() {
		return opertion;
	}

	/**
	 * @param opertion the opertion to set
	 */
	public void setOpertion(String opertion) {
		this.opertion = opertion;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
}