package com.ravi.service;

import java.util.List;

import javax.sql.DataSource;

import com.ravi.constants.AppConstants;
import com.ravi.mappers.StockHistRowMapper;
import com.ravi.mappers.StockRowMapper;
import com.ravi.model.Stock;
import com.ravi.model.StockHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("simple-cache")
public class DBRepo {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dsStk") DataSource stkDB) {
        this.jdbcTemplate = new JdbcTemplate(stkDB);
    }
    
    public List<Stock> getStocksToBeTracked() {
        List<Stock> stocks = null;

        stocks = this.jdbcTemplate.query(AppConstants.GET_STOCKS_FOR_TRACKING, new StockRowMapper());
        
        return stocks;
    }

    public List<StockHistory> getStockHistory(int stockId) {
        List<StockHistory> hist = null;

        hist = this.jdbcTemplate.query(AppConstants.GET_STOCK_HISTORY_BY_ID, new Object[] { stockId }, new StockHistRowMapper());
        
        return hist;
    }

    public int updateTrackingStatus() {
        return this.jdbcTemplate.update(AppConstants.UPDATE_TRACKING_MARKER);
    }

    public int stopTracking(Stock stock) {
        return this.jdbcTemplate.update(AppConstants.UPDATE_TRACKING_COMPLETE, new Object[] {AppConstants.COMPLETED, stock.getStockId()});
    }

    public int addHistory(StockHistory hist) {
        return this.jdbcTemplate.update(AppConstants.INSERT_STOCK_HIST, new Object[] {hist.getStockId(), hist.getMarketPrice(), hist.getSquareOff(), hist.getSellOff(), hist.getStatus()});
    }
}