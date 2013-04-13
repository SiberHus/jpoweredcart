package com.jpoweredcart.common.system.media;

public interface MediaService {

	/**
	 * 
	 * @param path is the relative image path
	 * @return
	 */
	public String getImageUrl(String path);
	
	/**
	 * 
	 * @param path is the relative image path
	 * @param width
	 * @param height
	 * @return
	 */
	public String getThumbnailUrl(String path, int width, int height);
	
	public String getThumbnailUrl(String path);
	
}
