package com.jpoweredcart.common.service.impl;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import com.jpoweredcart.common.service.ImageService;

public abstract class AbstractImageService implements ImageService {
	
	private String baseUrl;
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		if(baseUrl==null){
			throw new IllegalArgumentException("baseUrl cannot be null");
		}
		if(!baseUrl.endsWith("/")){
			baseUrl += "/";
		}
		this.baseUrl = baseUrl;
	}
	
	protected BufferedImage resizeImage(BufferedImage originalImage,
			int imgWidth, int imgHeight, int type) {
		BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
		g.dispose();

		return resizedImage;
	}
	
	protected BufferedImage resizeImageWithHint(
			BufferedImage originalImage, int imgWidth, int imgHeight, int type) {

		BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight,
				type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		return resizedImage;
	}
	
}
