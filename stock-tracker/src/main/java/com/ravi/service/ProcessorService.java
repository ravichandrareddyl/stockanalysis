package com.ravi.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ravi.model.Quote;
import com.ravi.model.Stock;
import com.ravi.model.StocksResponseModel;
import com.ravi.util.AppUtil;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProcessorService {
    
    Logger logger = LoggerFactory.getLogger(ProcessorService.class);

    public String process(final Stock stock) {
        logger.info("inside process method");
        String quoteResponse = this.getQuote(stock);
        Quote quote = this.parseQuote(quoteResponse);

        
        
        return null;
    }

    private Quote parseQuote(final String response) {
        Quote quote = null;
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        try {
            StocksResponseModel data = mapper.readValue(response, StocksResponseModel.class);
            quote = data.getData().size() > 0 ? data.getData().get(0) : null;
        } catch (Exception e) {
            logger.error("failed to covert stock object to string with error {}", e.getMessage());
        }
        return quote;
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

            logger.info("printing stock data: {}", jsonData);
            
         } catch (HttpStatusException ex) {
            //...
         } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        return jsonData;
    }
}