package com.stocks.analysis.filters;

import java.util.Map;
import java.util.function.Function;

import org.springframework.integration.file.filters.AbstractMarkerFilePresentFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;

import com.jcraft.jsch.ChannelSftp.LsEntry;
public class SftpFileMarkerFilter extends AbstractMarkerFilePresentFileListFilter<LsEntry>{

	public SftpFileMarkerFilter(FileListFilter<LsEntry> filter) {
		super(filter);
	}

	public SftpFileMarkerFilter(FileListFilter<LsEntry> filter, String suffix) {
		super(filter, suffix);
	}

	public SftpFileMarkerFilter(FileListFilter<LsEntry> filter,
			Function<String, String> function) {
		super(filter, function);
	}

	public SftpFileMarkerFilter(
			Map<FileListFilter<LsEntry>, Function<String, String>> filtersAndFunctions) {
		super(filtersAndFunctions);
	}
	
	@Override
	protected String getFilename(LsEntry arg0) {
		// TODO Auto-generated method stub
		return arg0.getFilename();
	}

}
