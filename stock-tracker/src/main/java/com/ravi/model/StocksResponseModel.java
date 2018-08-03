package com.ravi.model;

import java.util.List;

public class StocksResponseModel {
    
    private List<Quote> data;

	/**
	 * @return the data
	 */
	public List<Quote> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<Quote> data) {
		this.data = data;
	}
}
