package com.jpoweredcart.common.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

public class FileSystemImageService extends AbstractImageService {
	
	private String baseDir;
	
	@Override
	public void storeImage(File imgFile, String newName) {
		
	}

	@Override
	public void resizeAndStoreImage(File imgFile, String newName, int newWidth,
			int newHeight, ImageType outputType) throws IOException {
		
		BufferedImage originalImage = ImageIO.read(imgFile);
		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		
		BufferedImage resizedImage = resizeImage(originalImage, newWidth, newHeight, type);
		File newImageFile = new File(getBaseDir()+newName);
		ImageIO.write(resizedImage, outputType.name(), newImageFile);
	}
	
	@Override
	public String getImageUrl(String imageFileName) {
		
		return getBaseUrl()+imageFileName;
	}
	
	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		if(StringUtils.isBlank(baseDir)){
			throw new IllegalArgumentException("baseDir couldn't be empty");
		}else{
			File baseFileDir = new File(baseDir);
			if(!baseFileDir.exists()){
				if(!baseFileDir.mkdir()){
					throw new IllegalArgumentException("baseDir: '"+baseDir
							+"' does not exist and the system could not create it fot you.");
				}
			}
		}
		if(!(baseDir.endsWith("/") && baseDir.endsWith(File.separator))){
			this.baseDir += File.separator; 
		}
		this.baseDir = baseDir;
	}
	
}
