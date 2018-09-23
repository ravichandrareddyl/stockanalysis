package com.ravi.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ravi.constants.AppConstants;
import com.ravi.model.Stock;

import org.springframework.jdbc.core.RowMapper;

public class StockRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Stock stock = new Stock();
        stock.setStock(rs.getString(AppConstants.STOCK));
        stock.setStockId(rs.getInt(AppConstants.ID));
        stock.setOperation(rs.getString(AppConstants.OPERATION));
        stock.setPrice(rs.getBigDecimal(AppConstants.PRICE));
        stock.setTrackingStatus(rs.getString(AppConstants.TRACKING_STATUS));
        stock.setAlertStatus(rs.getString(AppConstants.ALERT_STATUS));
        stock.setPercent(rs.getDouble(AppConstants.PERCENT));
        stock.setCurrentPrice(rs.getBigDecimal(AppConstants.CURRENT_PRICE));
		return stock;
	}

}