package com.jpoweredcart.common.system.media;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpoweredcart.common.utils.ImageUtils;
import com.jpoweredcart.common.utils.PathUtils;

public class DefaultMediaService implements MediaService {
	
	public static final int THUMB_WIDTH = 100;
	
	public static final int THUMB_HEIGHT = 100;
	
	public static final String NO_IMG_PATH = "no_image.jpg";
	
	protected static Logger logger = LoggerFactory.getLogger(DefaultMediaService.class);
	
	private String imageDir;
	
	private String imageBaseUrl;
	
	private String thumbnailDir;
	
	private String thumbnailBaseUrl;
	
	public DefaultMediaService(){
	}
	
	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = PathUtils.ensureEndingFileSeparator(imageDir);
	}
	
	public String getImageBaseUrl() {
		return imageBaseUrl;
	}

	public void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = PathUtils.ensureEndingSlash(imageBaseUrl);
	}

	public String getThumbnailDir() {
		return thumbnailDir;
	}

	public void setThumbnailDir(String thumbnailDir) {
		this.thumbnailDir = PathUtils.ensureEndingFileSeparator(thumbnailDir);
	}

	public String getThumbnailBaseUrl() {
		return thumbnailBaseUrl;
	}

	public void setThumbnailBaseUrl(String thumbnailBaseUrl) {
		this.thumbnailBaseUrl = PathUtils.ensureEndingSlash(thumbnailBaseUrl);
	}

	@Override
	public String getImageUrl(String path) {
		
		return getImageBaseUrl() + path;
	}
	
	@Override
	public String getThumbnailUrl(String path, int width, int height) {
		
		if(StringUtils.isBlank(path)){
			return getImageUrl(NO_IMG_PATH);
		}
		
		String directory = FilenameUtils.getPath(path);
		String baseName = FilenameUtils.getBaseName(path);
		String ext = FilenameUtils.getExtension(path);
		
		String thumbnailPath = directory+baseName+"_"+width+"x"+height+"."+ext;
		File thumbnailFile = new File(getThumbnailDir()+thumbnailPath);
		if(!thumbnailFile.exists()){
			File imageFile = new File(getImageDir()+"data"+File.separator+path);
			if(!imageFile.exists()){
				logger.warn("Image file not found: "+imageFile.getAbsolutePath());
				return getImageUrl(NO_IMG_PATH);
			}
			try {
				File parentDir = thumbnailFile.getParentFile();
				if(!parentDir.exists()){
					parentDir.mkdirs();
				}
				ImageUtils.resizeImage(imageFile, width, height, thumbnailFile);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		thumbnailPath = thumbnailPath.replaceAll("[\\\\|\\\\/|/]+", "/");
		
		return getThumbnailBaseUrl() + thumbnailPath;
	}
	
	@Override
	public String getThumbnailUrl(String path){
		return getThumbnailUrl(path, THUMB_WIDTH, THUMB_HEIGHT);
	}
	
}
