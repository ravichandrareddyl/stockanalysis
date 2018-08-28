package com.ravi.constants;

public interface AppConstants {

    public static final String STOCK_PLACEHOLDER = "{name}";
    
    public static final String NSE_QUOTE_URL = "https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol={name}&illiquid=0&smeFlag=0&itpFlag=0";


    public static final String JOB_CREATOR = "JOB_CREATOR";
    public static final String JOB_CREATOR_GROUP = "ADMIN";

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
    public static final String CURRENT_PRICE = "MARKET_PRICE";

    //STOCK_MASTER_DETAIL table columns
    public static final String STOCK_ID = "STOCK_ID";
    public static final String STOCK_HIST_ID = "STOCK_HIST_ID";
    public static final String MARKET_PRICE = "MARKET_PRICE";
    public static final String SQUARE_OFF = "SQUARE_OFF";
    public static final String SELL_OFF = "SELL_OFF";
    public static final String STATUS = "STATUS";

    //Queries

    public static final String GET_ALL_STOCKS = "SELECT M.*, D1.MARKET_PRICE FROM STOCKS_TRACKING_DETAIL D1, STOCKS_MASTER M " 
    + " WHERE M.ID = D1.STOCK_ID  "
    + " and D1.STOCK_HIST_ID=(SELECT MAX(STOCK_HIST_ID) " 
    + " from STOCKS_TRACKING_DETAIL D2 where D2.STOCK_ID = D1.STOCK_ID); ";

    public static final String GET_STOCKS_FOR_TRACKING = "SELECT M.*, 0 as MARKET_PRICE FROM STOCKS_MASTER M WHERE TRACKING_STATUS='TRACKING';";

    public static final String GET_STOCKS_FOR_TRACKING_NEW = "SELECT M.*, 0 as MARKET_PRICE FROM STOCKS_MASTER M WHERE TRACKING_STATUS='' ;";

    public static final String UPDATE_TRACKING_MARKER = "UPDATE STOCKS_MASTER SET TRACKING_STATUS='TRACKING' WHERE TRACKING_STATUS='';";

    public static final String UPDATE_TRACKING_COMPLETE = "UPDATE STOCKS_MASTER SET TRACKING_STATUS = ? WHERE ID = ? ;";

    public static final String INSERT_STOCK_HIST = "INSERT INTO STOCKS_TRACKING_DETAIL (STOCK_ID, MARKET_PRICE, SQUARE_OFF, SELL_OFF, STATUS) VALUES (?, ?, ?, ?, ?);";

    public static final String UPDATE_SOLD_STATUS = "UPDATE STOCKS_TRACKING_DETAIL SET STATUS = ? where STOCK_HIST_ID = ? ;";

    public static final String GET_STOCK_HISTORY_BY_ID = "SELECT * FROM STOCKS_TRACKING_DETAIL D1 WHERE STOCK_ID = ? and STOCK_HIST_ID=(SELECT MAX(STOCK_HIST_ID) from STOCKS_TRACKING_DETAIL D2 where D2.STOCK_ID = D1.STOCK_ID);";
     
    public static final String JOB_GROUP_NAME = "STOCKS";

    public static final String START_UP = "STARTUP";

    public static final String RUNTIME = "RUNTIME";

    public static final String RUN_TIME_EXECUTOR_CRON = "0 0/15 * ? * MON,TUE,WED,THU,FRI *";

}