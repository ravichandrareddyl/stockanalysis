package com.stocks.analysis.constants;

public class AppConstants {
	
	//start: input columns
	public static final String[] BAV_COLUMNS = {"symbol", "series", "open", "high", "low", "close", "last", "prevClose", "totTrdQty", "totTrdVal", "asOnDate", "totalTrades", "IsIn", "dummy"};
	
	//end: input columns
	
	//start app related strings
	
	public static final int EIGHT = 8;
	
	//end app related strings
	
	//start: input file prefixes
	
	public static final String BAV_PREFIX = "cm";
	//end: input file prefixes
	
	//start: channel types
	public static final String BAV_CHANNEL = "bavChannel";
	
	public static final String NULL_CHANNEL = "nullChannel";
	
	public static final String FILE_INPUT_CHANNEL = "fileInputChannel";
	
	public static final String ERROR_CHANNEL = "errorChannel";
	
	public static final String ROUTING_CHANNEL = "routingChannel";
	
	public static final String JOB_LISTENER_CHANNEL = "jobListenerChannel";
	//end: channel types
	
	
	//start: source types
	public static final String NSE = "NSE";
	//end: source types
	
	public static final String INBOUND_PATH="D:\\Ravichandra\\Projects\\recon\\TestFolder";
	
	//start: poll size
	public static final int MAX_FETCH_SIZE = 1;
	
	public static final int MAX_MESSAGES_PER_POLL = 1;
	//end: poll size
	
	public static final String NOT_AVAILABLE = "NA";
	
	public static final double ZERO_AMT = 0.00;
	
	public static final String ZERO_STRING = "0";
	
	public static final String EMPTY_STRING = "";
	
	public static final String JOB_ERROR = ".error";

	public static final String JOB_PROCESSED = ".processed";
	
	public static final String FILE_WRITING = ".writing";
	
	public static final String JOB_FAILED = "FAILED";
	
	public static final String JOB_FILE_NAME_PARAM = "input.file.name";

	public static final String JOB_FILE_SOURCE_PARAM = "input.file.type";
	
	public static final String JOB_AS_ON_DATE = "input.file.asOnDate";
	
	//start folder names
	
	public static final String LOCAL_FOLDER = "/tmp/inbound";
	
	//end folder names
	
	// start chunck size
	
	public static final int CHUNK_SIZE = 50;
	
	public static final long REMOTE_POLLING_DURATION = 30000;
	
	public static final long LOCAL_POLLING_DURATION = 30000;
	
	//end chunck size
	
	//start app queries
	public static final String BAV_WRITER_SQL = "INSERT INTO BAV_DATA (SYMBOL,SERIES,OPEN,HIGH,LOW,"
				+ "CLOSE,LAST,PREVCLOSE,TOTTRDQTY,TOTTRDVAL,AS_ON_DATE,TOTALTRADES,ISIN) VALUES (:symbol,:series, "
				+ "CAST(:open as money),CAST(:high as money),CAST(:low as money),CAST(:close as money),"
				+ "CAST(:last as money),CAST(:prevClose as money), CAST(:totTrdQty as integer), CAST(:totTrdVal as money), TO_TIMESTAMP(:asOnDate, 'DD-MON-YYYY'),CAST(:totalTrades as integer), :isIn)";
	
	
	//end app queries
}
