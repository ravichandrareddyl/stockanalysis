package com.ravi.constants;

public interface AppConstants {

    public static final String STOCK_PLACEHOLDER = "{name}";
    
    public static final String NSE_QUOTE_URL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol={name}&illiquid=0&smeFlag=0&itpFlag=0";

    public static final String SOLD = "SOLD";
    public static final String COMPLETED = "COMPLETED";
    //STOCK_MASTER table columns
    public static final String STOCK = "NAME";
    public static final String OPERATION = "OPERTION";
    public static final String PRICE = "PRICE";
    public static final String TRACKING_STATUS = "TRACKING_STATUS";
    public static final String ALERT_STATUS = "ALERT_STATUS";
    public static final String ID = "ID";
    public static final String PERCENT ="PERCENT";

    //STOCK_MASTER_DETAIL table columns
    public static final String STOCK_ID = "STOCK_ID";
    public static final String STOCK_HIST_ID = "STOCK_HIST_ID";
    public static final String MARKET_PRICE = "MARKET_PRICE";
    public static final String SQUARE_OFF = "SQUARE_OFF";
    public static final String SELL_OFF = "SELL_OFF";
    public static final String STATUS = "STATUS";

    //Queries

    public static final String GET_STOCKS_FOR_TRACKING = "SELECT * FROM STOCKS_MASTER WHERE (TRACKING_STATUS='' OR TRACKING_STATUS='TRACKING') ;";

    public static final String UPDATE_TRACKING_MARKER = "UPDATE STOCKS_MASTER SET TRACKING_STATUS='TRACKING' WHERE TRACKING_STATUS='';";

    public static final String UPDATE_TRACKING_COMPLETE = "UPDATE STOCKS_MASTER SET TRACKING_STATUS = ? WHERE ID = ? ;";

    public static final String INSERT_STOCK_HIST = "INSERT INTO STOCKS_TRACKING_DETAIL (STOCK_ID, MARKET_PRICE, SQUARE_OFF, SELL_OFF, STATUS) VALUES (?, ?, ?, ?, ?);";

    public static final String UPDATE_SOLD_STATUS = "UPDATE STOCKS_TRACKING_DETAIL SET STATUS = ? where STOCK_HIST_ID = ? ;";

    public static final String GET_STOCK_HISTORY_BY_ID = "SELECT * FROM STOCKS_TRACKING_DETAIL D1 WHERE STOCK_ID = ? and STOCK_HIST_ID=(SELECT MAX(STOCK_HIST_ID) from STOCKS_TRACKING_DETAIL D2 where D2.STOCK_ID = D1.STOCK_ID);";
     
    public static final String JOB_GROUP_NAME = "STOCKS";

}