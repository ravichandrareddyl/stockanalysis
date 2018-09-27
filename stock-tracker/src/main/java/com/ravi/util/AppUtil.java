package com.ravi.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static int getRandomnNum() {
        Random rand = new Random();
        return rand.nextInt(59) + 1;
    }

    public static String getCronExpression() {
        int rand = getRandomnNum();
        return String.format("%d * 9-15 ? * MON,TUE,WED,THU,FRI *", rand);
    }

    public static BigDecimal getSquareOffValue(BigDecimal in, double percent) {
        return in.add((in.multiply(new BigDecimal((percent))).divide(new BigDecimal(100))));
    }


    public static BigDecimal getSellOffValue(BigDecimal in, double percent) {
        return in.subtract((in.multiply(new BigDecimal((0.5))).divide(new BigDecimal(100))));
    }

    public static String valueFromKey(String json) {
        String result = null;

        Pattern pattern = Pattern.compile("\"lastPrice\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

}