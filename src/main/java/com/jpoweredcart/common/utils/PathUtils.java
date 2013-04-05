package com.jpoweredcart.common.utils;

import java.io.File;

public class PathUtils {
	
	public static String ensureEndingSlash(String path) {
		if (path == null) {
			throw new IllegalArgumentException("path cannot be null");
		}
		if (!path.endsWith("/")) {
			path += "/";
		}
		return path;
	}
	
	public static String ensureEndingFileSeparator(String path){
		if (path == null) {
			throw new IllegalArgumentException("path cannot be null");
		}
		if(!(path.endsWith("/") || path.endsWith("\\"))){
			return path+File.separator;
		}
		return path;
	}
	
}
