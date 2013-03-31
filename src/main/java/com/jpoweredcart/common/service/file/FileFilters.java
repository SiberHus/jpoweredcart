package com.jpoweredcart.common.service.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public abstract class FileFilters {
	
	public static List<String> MEDIA_TYPES;
	
	static{
		List<String> mediaTypeList = new ArrayList<String>();
		mediaTypeList.add("jpg");
		mediaTypeList.add("jpeg");
		mediaTypeList.add("png");
		mediaTypeList.add("gif");
		mediaTypeList.add("flv");
		MEDIA_TYPES = Collections.unmodifiableList(mediaTypeList);
	}
	
	public static class DirectoryFilter implements FileFilter{
		
		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
		
	}
	
	public static class ExtensionFileFilter implements FileFilter {
		
		private List<String> extensions = null;
		
		public ExtensionFileFilter(List<String> extensions){
			this.extensions = extensions;
		}
		
		@Override
		public boolean accept(File file) {
			String ext = FilenameUtils.getExtension(file.getName());
			return extensions.contains(ext.toLowerCase());
		}
	}
	
	public static class MediaFileFilter extends ExtensionFileFilter {
		
		public MediaFileFilter() {
			super(FileFilters.MEDIA_TYPES);
		}
		
	}
	
}
