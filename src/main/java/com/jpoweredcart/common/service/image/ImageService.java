package com.jpoweredcart.common.service.image;

import java.io.IOException;

public interface ImageService {
	
	public static enum ImageType {
		JPG, PNG, GIF, BMP
	}
	
	public void storeImage(String imgPath, String newName) throws IOException;
	
	public void createThumbnail(String imgPath, int newWidth, int newHeight) throws IOException;
	
	public String getImageUrl(String path);
	
	public String getThumbnailUrl(String path);
}
