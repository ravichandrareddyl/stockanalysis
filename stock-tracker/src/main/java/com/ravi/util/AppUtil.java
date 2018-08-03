package com.ravi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ravi.constants.AppConstants;
import com.ravi.model.Stock;

public class AppUtil {

    public static String getDateString(Date date, String format) {

        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getNseUrl(Stock stock) {
        return AppConstants.NSE_QUOTE_URL.replace(AppConstants.STOCK_PLACEHOLDER, stock.getStock());
    }
    
}