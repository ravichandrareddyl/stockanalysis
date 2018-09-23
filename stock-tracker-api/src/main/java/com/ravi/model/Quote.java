package com.ravi.model;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("custom_serializer")
public class Quote {
	private String lastPrice;
	private String pricebandupper;

	/**
	 * @return the lastPrice
	 */
	public String getLastPrice() {
		return lastPrice;
	}

	/**
	 * @return the pricebandupper
	 */
	public String getPricebandupper() {
		return pricebandupper;
	}

	/**
	 * @param pricebandupper the pricebandupper to set
	 */
	public void setPricebandupper(String pricebandupper) {
		this.pricebandupper = pricebandupper;
	}

	/**
	 * @param lastPrice the lastPrice to set
	 */
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}

}