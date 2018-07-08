package com.stocks.analysis.filters;

import java.io.File;
import java.util.Map;
import java.util.function.Function;

import org.springframework.integration.file.filters.AbstractMarkerFilePresentFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;

public class FileSystemMarkerFilter extends AbstractMarkerFilePresentFileListFilter<File> {

	public FileSystemMarkerFilter(FileListFilter<File> filter) {
		super(filter);
	}

	public FileSystemMarkerFilter(FileListFilter<File> filter, String suffix) {
		super(filter, suffix);
	}

	public FileSystemMarkerFilter(FileListFilter<File> filter,
			Function<String, String> function) {
		super(filter, function);
	}

	public FileSystemMarkerFilter(
			Map<FileListFilter<File>, Function<String, String>> filtersAndFunctions) {
		super(filtersAndFunctions);
	}

	@Override
	protected String getFilename(File file) {
		return file.getName();
	}

}