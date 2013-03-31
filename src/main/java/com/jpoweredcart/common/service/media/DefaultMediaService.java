package com.jpoweredcart.common.service.media;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import com.jpoweredcart.common.service.file.FileService;
import com.jpoweredcart.common.utils.ImageUtils;

public class DefaultMediaService implements MediaService {
	
	private FileService mediaFileService;
	
	private String imageDir;
	
	private String imageBaseUrl;
	
	private String thumbnailDir;
	
	private String thumbnailBaseUrl;
	
	public DefaultMediaService(FileService mediaFileService){
		this.mediaFileService = mediaFileService;
	}
	
	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = mediaFileService.ensureEndingSlash(imageDir);
	}

	public String getImageBaseUrl() {
		return imageBaseUrl;
	}

	public void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = checkBaseUrl(imageBaseUrl);
	}

	public String getThumbnailDir() {
		return thumbnailDir;
	}

	public void setThumbnailDir(String thumbnailDir) {
		this.thumbnailDir = mediaFileService.ensureEndingSlash(thumbnailDir);
	}

	public String getThumbnailBaseUrl() {
		return thumbnailBaseUrl;
	}

	public void setThumbnailBaseUrl(String thumbnailBaseUrl) {
		this.thumbnailBaseUrl = checkBaseUrl(thumbnailBaseUrl);
	}

	@Override
	public String getImageUrl(String path) {
		
		return getImageBaseUrl() + path;
	}

	@Override
	public String getThumbnailUrl(String path, int width, int height) {
		
		String directory = FilenameUtils.getPath(path);
		String baseName = FilenameUtils.getBaseName(path);
		String ext = FilenameUtils.getExtension(path);
		
		String thumbnailPath = directory+baseName+"_"+width+"x"+height+"."+ext;
		File thumbnailFile = new File(getThumbnailDir()+thumbnailPath);
		if(!thumbnailFile.exists()){
			File imageFile = new File(getImageDir()+"data"+File.separator+path);
			if(!imageFile.exists()){
				throw new RuntimeException("Image file not found: "+imageFile.getAbsolutePath());
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
	
	protected String checkBaseUrl(String url) {
		if (url == null) {
			throw new IllegalArgumentException("url cannot be null");
		}
		if (!url.endsWith("/")) {
			url += "/";
		}
		return url;
	}
}
