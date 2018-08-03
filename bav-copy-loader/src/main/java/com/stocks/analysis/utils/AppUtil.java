package com.stocks.analysis.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.function.Function;

import com.stocks.analysis.constants.AppConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AppUtil {
	
	private static final Logger log = LoggerFactory.getLogger(AppUtil.class);
	
	public static String getSourceName(String name) {
		if (name.contains(AppConstants.BAV_PREFIX)) {
			return AppConstants.NSE;
		}
		return null;
	}

	public static void main(String[] args) {
		String file = "cm28MAY2018bhav.csv";
		System.out.println(getAsOnDate(file));
	}
	
	public static Date getAsOnDate(String file) {
		String fileName = getFileName(file);
		String[] words = fileName.split("cm");
		
		Date date = new Date();
		String dateString = words[1].split("bhav")[0];
		try {
			if (null != dateString) {
					date = new SimpleDateFormat("ddMMMyyyy").parse(dateString);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		
		return date;
	}
	
	// public static Map<FileListFilter<File>, Function<String, String>> getSameFilterMap() {
	// 	Map<FileListFilter<File>, Function<String, String>> map = new HashMap<>();
    //     map.put(new SimplePatternFileListFilter("*.txt"), AppUtil.getSameFileName());
    //     map.put(new SimplePatternFileListFilter("*.csv"), AppUtil.getSameFileName());
		
	// 	return map;
	// }
	
	// public static Map<FileListFilter<File>, Function<String, String>> getFilterMap() {
	// 	Map<FileListFilter<File>, Function<String, String>> map = new HashMap<>();
    //     map.put(new SimplePatternFileListFilter("*.txt"), AppUtil.getMarkerFileName());
    //     map.put(new SimplePatternFileListFilter("*.csv"), AppUtil.getMarkerFileName());
		
	// 	return map;
	// }
	
	// public static Map<FileListFilter<LsEntry>, Function<String, String>> getSftpFilterMap() {
	// 	Map<FileListFilter<LsEntry>, Function<String, String>> map = new HashMap<>();
    //     map.put(new SftpSimplePatternFileListFilter("*.txt"), AppUtil.getMarkerFileName());
    //     map.put(new SftpSimplePatternFileListFilter("*.csv"), AppUtil.getMarkerFileName());
		
	// 	return map;
	// }
	
	public static String getFileName (String completeName) {
		if(completeName != null && completeName.indexOf(".") > -1) {
			//log.info("file has extension" + completeName);
			String[] words = completeName.split("\\.");
			if (words.length == 2)
				return words[0];
		}
		return completeName;
	}
	
	public static Function<String, String> getSameFileName() {
		return s -> {
			System.out.println("prinintg file name"+ s);
			return s;
		};
	}

	// public static Function<String, String> getMarkerFileName() {
	// 	return s -> {
	// 		String markerFileName = null;
	// 		log.info("filename in util function" + s);
	// 		s = getFileName(s);
				
	// 		if (s.startsWith(AppConstants.PHUB_PREFIX)) {
	// 			String[] words = s.split("_");
	// 			if (words.length == 3)
	// 				markerFileName = "phub_copy_completed_" + words[2] + ".tch";
	// 		} else if (s.startsWith(AppConstants.FC_PREFIX)
	// 				|| s.startsWith(AppConstants.FT_PREFIX)
	// 				|| s.startsWith(AppConstants.SC_PREFIX)
	// 				|| s.startsWith(AppConstants.TI_PREFIX)
	// 				) {
	// 			//String[] words = s.split(".");
	// 			markerFileName = s + ".TCH";
	// 		} else if (s.startsWith(AppConstants.SOC_PREFIX)) {
	// 			markerFileName = s + ".TCH";
	// 		}
	// 		log.info("marker file name is===>" + markerFileName);
	// 		return markerFileName;	
	// 	};
	// }

	public static Function<File, String> determineChannels () {
		return s -> {
			String targetChannel = AppConstants.NULL_CHANNEL;
			log.debug("Input file name is {}", s);
		if (s.getName().startsWith(AppConstants.BAV_PREFIX)) {
			Date asOnDate = getAsOnDate(s.getName());
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(asOnDate);
			log.info("year ===> {}0", calendar.get(Calendar.YEAR));
			if (calendar.get(Calendar.YEAR) <= 2011) {
				targetChannel = AppConstants.OLD_BAV_CHANNEL;
			} else {
				targetChannel = AppConstants.BAV_CHANNEL;
			}
			
		}
		log.info("Target channel is {}", targetChannel); 
		return targetChannel;
		};
	}

	public static void renameFile(String fileName, String suffix) {
		Assert.notNull(fileName, "filename should not be null");
		Assert.notNull(suffix, "Suffix should not be null");

		File file = new File(fileName);
		file.renameTo(new File(file + suffix));
	}
}
