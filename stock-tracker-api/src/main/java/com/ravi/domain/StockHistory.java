package com.ravi.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "STOCKS_TRACKING_DETAIL", uniqueConstraints = { 
	@UniqueConstraint(columnNames = { "STOCK_HIST_ID" })
})
public class StockHistory {

	@Column(name="stock_id")
	private int stockId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="STOCK_HIST_ID")
	private int stockHistId;
	
	@Column(name="market_price")
	private BigDecimal marketPrice;
	
	@Column(name="square_off")
	private BigDecimal squareOff;
	
	@Column(name="sell_off")
	private BigDecimal sellOff;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stock_Id", nullable = true)
	private Stock stock;

    private String status;
	/**
	 * @return the stockId
	 */
	public int getStockId() {
		return stockId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the sellOff
	 */
	public BigDecimal getSellOff() {
		return sellOff;
	}
	/**
	 * @param sellOff the sellOff to set
	 */
	public void setSellOff(BigDecimal sellOff) {
		this.sellOff = sellOff;
	}
	/**
	 * @return the squareOff
	 */
	public BigDecimal getSquareOff() {
		return squareOff;
	}
	/**
	 * @param squareOff the squareOff to set
	 */
	public void setSquareOff(BigDecimal squareOff) {
		this.squareOff = squareOff;
	}
	/**
	 * @return the marketPrice
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	/**
	 * @param marketPrice the marketPrice to set
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	/**
	 * @return the stockHistId
	 */
	public int getStockHistId() {
		return stockHistId;
	}
	/**
	 * @param stockHistId the stockHistId to set
	 */
	public void setStockHistId(int stockHistId) {
		this.stockHistId = stockHistId;
	}
	/**
	 * @param stockId the stockId to set
	 */
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

}