package com.ravi.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.ravi.constants.AppConstants;
import com.ravi.model.Stock;
import com.ravi.model.StockHistory;
import com.ravi.scheduling.JobSchedulerModelGenerator;
import com.ravi.util.AppUtil;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {
    
    Logger logger = LoggerFactory.getLogger(ProcessorService.class);

    @Autowired
    private DBRepo dao;

    @Autowired
    private JobSchedulerModelGenerator jobSchedulerModelGenerator;

    public String process(final Stock stock) {
        String quoteResponse = this.getQuote(stock);
        BigDecimal marketPrice = this.parseQuote(quoteResponse);

        if(AppConstants.OPR_BUY.equalsIgnoreCase(stock.getOperation())) {
            this.trackBuy(quoteResponse, marketPrice, stock);
        } else if (AppConstants.OPR_SELL.equalsIgnoreCase(stock.getOperation())) {
            this.trackSell(quoteResponse, marketPrice, stock);
        }
        return null;
    }

    private void trackSell(String quoteResponse, BigDecimal marketPrice, Stock stock) {
        double percent = 0 != stock.getPercent() ? stock.getPercent(): 1.0;
        BigDecimal  sellOff = AppUtil.getSquareOffValue(marketPrice, 0.5);
        BigDecimal sqrOff = AppUtil.getSellOffValue(marketPrice, percent);

        logger.info("Stock: {} and Price: {}, square off: {}, sell off : {}", stock.getStock(), marketPrice, sqrOff, sellOff);
        List<StockHistory> previousHistory = dao.getStockHistory(stock.getStockId());
        
        if (previousHistory.size() > 0) {
            //has history
            StockHistory hist = previousHistory.get(0);
            if (marketPrice.compareTo(hist.getMarketPrice()) == -1) {
                logger.info("down trend continues");
                this.addHistory(stock, marketPrice, sqrOff, sellOff, null);
            } else {
                if (marketPrice.compareTo(hist.getSellOff()) >= 0) {
                    logger.info("market price greater than sell off price so BUYING it and deleting job");
                    this.addHistory(stock, marketPrice, hist.getSquareOff(), hist.getSellOff(), AppConstants.BOUGHT);
                    this.jobSchedulerModelGenerator.deleteJob(stock);
                }
                //
            }
        } else {
            //no history
            logger.info("stock price is : {}", stock.getPrice());
            if (stock.getPrice().compareTo(sellOff) >= 0) {
                logger.info("price greater than sell off so BUYING it it");
                this.addHistory(stock, marketPrice, sqrOff, sellOff, AppConstants.BOUGHT);
                this.jobSchedulerModelGenerator.deleteJob(stock);
            } else {
                logger.info("tracking the order as it is not greater than sell of price");
                this.addHistory(stock, marketPrice, sqrOff, sellOff, null);
            }

        }
    }

    private void trackBuy(String quoteResponse, BigDecimal marketPrice, Stock stock) {
        double percent = 0 != stock.getPercent() ? stock.getPercent(): 1.0;
        BigDecimal sqrOff = AppUtil.getSquareOffValue(marketPrice, percent);
        BigDecimal sellOff = AppUtil.getSellOffValue(marketPrice, 0.5);

        logger.info("Stock: {} and Price: {}, square off: {}, sell off : {}", stock.getStock(), marketPrice, sqrOff, sellOff);
        List<StockHistory> previousHistory = dao.getStockHistory(stock.getStockId());
        
        if (previousHistory.size() > 0) {
            //has history
            StockHistory hist = previousHistory.get(0);
            if (marketPrice.compareTo(hist.getMarketPrice()) == 1) {
                logger.info("Up trend continues");
                this.addHistory(stock, marketPrice, sqrOff, sellOff, null);
            } else {
                if (marketPrice.compareTo(hist.getSellOff()) <= 0) {
                    logger.info("market price less than sell off price so selling it and deleting job");
                    this.addHistory(stock, marketPrice, hist.getSquareOff(), hist.getSellOff(), AppConstants.SOLD);
                    this.jobSchedulerModelGenerator.deleteJob(stock);
                }
                //
            }
        } else {
            //no history
            logger.info("stock buy price is : {}", stock.getPrice());
            if (stock.getPrice().compareTo(sellOff) <= 0) {
                logger.info("buy price less than sell off so selling it");
                this.addHistory(stock, marketPrice, sqrOff, sellOff, AppConstants.SOLD);
                this.jobSchedulerModelGenerator.deleteJob(stock);
            } else {
                logger.info("tracking the order as it is not less that sell of price");
                this.addHistory(stock, marketPrice, sqrOff, sellOff, null);
            }

        }
    }

    private int addHistory(Stock stock, BigDecimal marketPrice, BigDecimal sqrOff, BigDecimal sellOff, String status) {
        StockHistory hist = new StockHistory();
        hist.setStockId(stock.getStockId());
        hist.setMarketPrice(marketPrice);
        hist.setSquareOff(sqrOff);
        hist.setSellOff(sellOff);
        if(null != status) {
            hist.setStatus(status);
        }
        return dao.addHistory(hist);
    }

    private BigDecimal parseQuote(final String response) {
        String price = AppUtil.valueFromKey(response);
        logger.debug("price from response: {}", price);
        String currency = price.replaceAll("\\,", "");
        return new BigDecimal(currency);
    }

    private String getQuote(Stock stock) {
        String jsonData = null;
        String quoteUrl = AppUtil.getNseUrl(stock);
        try {
            Document docCustomConn = Jsoup.connect(quoteUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .referrer(quoteUrl)
                .header("Accept", "*/*")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("X-Requested-With", "XMLHttpRequest")
                .get();
            
            Element ele = docCustomConn.getElementById("responseDiv");
            jsonData = ele.text();
            
         } catch (HttpStatusException ex) {
            //...
         } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        return jsonData;
    }
}