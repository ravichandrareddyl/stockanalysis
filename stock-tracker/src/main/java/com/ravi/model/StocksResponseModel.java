package com.ravi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("custom_serializer")
public class StocksResponseModel {
    
	private List<Quote> data;
	
	private String tradedDate;

	/**
	 * @return the data
	 */
	public List<Quote> getData() {
		return data;
	}

	/**
	 * @return the tradedDate
	 */
	public String getTradedDate() {
		return tradedDate;
	}

	/**
	 * @param tradedDate the tradedDate to set
	 */
	public void setTradedDate(String tradedDate) {
		this.tradedDate = tradedDate;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Quote> data) {
		this.data = data;
	}
}
