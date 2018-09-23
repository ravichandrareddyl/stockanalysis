// package com.ravi.mappers;

// import java.sql.ResultSet;
// import java.sql.SQLException;

// import com.ravi.constants.AppConstants;
// import com.ravi.model.StockHistory;

// import org.springframework.jdbc.core.RowMapper;

// public class StockHistRowMapper implements RowMapper {

// 	@Override
// 	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//         StockHistory hist = new StockHistory();
//         hist.setStockId(rs.getInt(AppConstants.STOCK_ID));
//         hist.setStockHistId(rs.getInt(AppConstants.STOCK_HIST_ID));
//         hist.setMarketPrice(rs.getBigDecimal(AppConstants.MARKET_PRICE));
//         hist.setSquareOff(rs.getBigDecimal(AppConstants.SQUARE_OFF));
//         hist.setSellOff(rs.getBigDecimal(AppConstants.SELL_OFF));
//         return hist;
// 	}

// }