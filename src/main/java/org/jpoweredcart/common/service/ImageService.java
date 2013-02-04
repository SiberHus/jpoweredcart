package org.jpoweredcart.common.service;

import java.io.File;
import java.io.IOException;

public interface ImageService {
	
	public static enum ImageType {
		JPG, PNG, GIF, BMP
	}
	
	public void storeImage(File imgFile, String newName) throws IOException;
	
	public void resizeAndStoreImage(File imgFile, String newName, 
			int newWidth, int newHeight, ImageType outputType) throws IOException;
	
	public String getImageUrl(String imgName);
	
}
