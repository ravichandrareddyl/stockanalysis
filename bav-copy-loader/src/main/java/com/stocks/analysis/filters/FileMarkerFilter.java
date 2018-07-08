package com.stocks.analysis.filters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.file.filters.FileListFilter;

public class FileMarkerFilter implements FileListFilter<File> {

	private static final Logger log = LoggerFactory.getLogger(FileMarkerFilter.class);

	public String getGenericKey(String file, Boolean isTch) {
		log.debug("In getGenericKey fileName" + file);
		log.debug("In getGenericKey isTch" + isTch);
		String fileName = file.lastIndexOf(".") > 0 ? file.substring(0, file.lastIndexOf(".")) : file;
		log.debug("In getGenericKey ==fileName"+ fileName);
		return fileName.toLowerCase();
	}

	public String getFileKey(File file, Boolean isTch) {
		String fileName = file.getName().toLowerCase();

		return getGenericKey(fileName, isTch);
	}

	@Override
	public List<File> filterFiles(File[] files) {
		// TODO Auto-generated method stub
		List<File> filteredFiles = new ArrayList<File>();
		Map<String, Boolean> tchStatus = new HashMap<String, Boolean>();
		
		for(File file : files) {
			if(file.isDirectory()) {
				continue;
			} else if(file.getName().contains(".tch")) {
				String fileKey = getFileKey(file, true);
				tchStatus.put(fileKey, true);
				//filteredFiles.add(file);
			}
		}
		
		log.debug("tchStatus is " + tchStatus.toString());
		for(File file : files) {
			if(file.isDirectory() || file.getName().contains(".tch")) {
				continue;
			} else {
				String fileKey = getFileKey(file, false);
				if (tchStatus.get(fileKey) != null)
					filteredFiles.add(file);
			}
		}
		
		
		return filteredFiles;
	}

}
