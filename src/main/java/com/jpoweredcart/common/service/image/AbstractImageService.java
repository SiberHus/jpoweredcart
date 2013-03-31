package com.jpoweredcart.common.service.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;


public abstract class AbstractImageService implements ImageService {
	
	private ImageType defaultImageType = ImageType.PNG;
	
	private String imageBaseUrl;
	
	private String thumbnailBaseUrl;
	
	public String getImageBaseUrl() {
		return imageBaseUrl;
	}

	public void setImageBaseUrl(String imageBaseUrl) {
		this.imageBaseUrl = checkBaseUrl(imageBaseUrl);
	}

	public String getThumbnailBaseUrl() {
		return thumbnailBaseUrl;
	}
	
	public void setThumbnailBaseUrl(String thumbnailBaseUrl) {
		this.thumbnailBaseUrl = checkBaseUrl(thumbnailBaseUrl);
	}

	@Override
	public String getImageUrl(String path) {
		
		return getImageBaseUrl()+path;
	}
	
	@Override
	public String getThumbnailUrl(String path) {
		
		return getThumbnailBaseUrl()+path;
	}
	
//	protected void resizeImage(File input, int width, int height, File output, 
//			ImageType imageType) throws IOException{
//		BufferedImage originalImage = ImageIO.read(input);
//		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
//		
//		BufferedImage resizedImage = resizeImage(originalImage, width, height, type);
//		
//		ImageIO.write(resizedImage, imageType.name(), output);
//	}
//	
//	protected BufferedImage resizeImage(BufferedImage originalImage,
//			int imgWidth, int imgHeight, int type) {
//		BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight,
//				type);
//		Graphics2D g = resizedImage.createGraphics();
//		g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
//		g.dispose();
//
//		return resizedImage;
//	}
	
//	protected BufferedImage resizeImageWithHint(
//			BufferedImage originalImage, int imgWidth, int imgHeight, int type) {
//
//		BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight,
//				type);
//		Graphics2D g = resizedImage.createGraphics();
//		g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
//		g.dispose();
//		g.setComposite(AlphaComposite.Src);
//
//		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		g.setRenderingHint(RenderingHints.KEY_RENDERING,
//				RenderingHints.VALUE_RENDER_QUALITY);
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//				RenderingHints.VALUE_ANTIALIAS_ON);
//		
//		return resizedImage;
//	}
	
	protected void resizeImage(File input, int width, int height, File output) throws IOException{
		
		String ext = FilenameUtils.getExtension(input.getName());
		ext = ext.toLowerCase();
		ImageType imageType = defaultImageType;
		if("jpg".equals(ext) || "jpeg".equals(ext)){
			imageType = ImageType.JPG;
		}else if("png".equals(ext)){
			imageType = ImageType.PNG;
		}else if("gif".equals(ext)){
			imageType = ImageType.GIF;
		}else if("bmp".equals(ext)){
			imageType = ImageType.BMP;
		}
		ScalrConfig defaultConfig = new ScalrConfig();
		resizeImage(input, width, height, output, imageType, defaultConfig);
		
	}
	
	protected void resizeImage(File input, int width, int height, File output, 
			ImageType imageType, ScalrConfig config) throws IOException{
		
		BufferedImage originalImage = ImageIO.read(input);
		BufferedImage thumbnail =
			Scalr.resize(originalImage, config.getMethod(), config.getMode(),
			width, height, config.getImageOp());
		ImageIO.write(thumbnail, imageType.name(), output);
		
	}
	
	protected String checkBaseUrl(String url){
		if(url==null){
			throw new IllegalArgumentException("url cannot be null");
		}
		if(!url.endsWith("/")){
			url += "/";
		}
		return url;
	}
	
}
