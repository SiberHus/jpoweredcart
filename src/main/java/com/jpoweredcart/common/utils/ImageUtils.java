package com.jpoweredcart.common.utils;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

public class ImageUtils {

	public static enum ImageType {
		JPG, PNG, GIF, BMP
	}
	
//	public static void resizeImage(File input, int width, int height, File output, 
//			ImageType imageType) throws IOException{
//		BufferedImage originalImage = ImageIO.read(input);
//		int type = originalImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
//		
//		BufferedImage resizedImage = resizeImage(originalImage, width, height, type);
//		
//		ImageIO.write(resizedImage, imageType.name(), output);
//	}
//	
//	public static BufferedImage resizeImage(BufferedImage originalImage,
//			int imgWidth, int imgHeight, int type) {
//		BufferedImage resizedImage = new BufferedImage(imgWidth, imgHeight,
//				type);
//		Graphics2D g = resizedImage.createGraphics();
//		g.drawImage(originalImage, 0, 0, imgWidth, imgHeight, null);
//		g.dispose();
//
//		return resizedImage;
//	}
//	
//	public static BufferedImage resizeImageWithHint(
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
	
	public static void resizeImage(File input, int width, int height, File output)
			throws IOException {

		String ext = FilenameUtils.getExtension(input.getName());
		ext = ext.toLowerCase();
		ImageType imageType = ImageType.JPG;
		if ("jpg".equals(ext) || "jpeg".equals(ext)) {
			imageType = ImageType.JPG;
		} else if ("png".equals(ext)) {
			imageType = ImageType.PNG;
		} else if ("gif".equals(ext)) {
			imageType = ImageType.GIF;
		} else if ("bmp".equals(ext)) {
			imageType = ImageType.BMP;
		}
		ScalrConfig defaultConfig = new ScalrConfig();
		resizeImage(input, width, height, output, imageType, defaultConfig);

	}

	public static void resizeImage(File input, int width, int height, File output,
			ImageType imageType, ScalrConfig config) throws IOException {

		BufferedImage originalImage = ImageIO.read(input);
		BufferedImage thumbnail = Scalr.resize(originalImage,
				config.getMethod(), config.getMode(), width, height,
				config.getImageOp());
		ImageIO.write(thumbnail, imageType.name(), output);

	}
	
	
	public static class ScalrConfig {
		
		private Scalr.Method method = Scalr.Method.QUALITY;
		
		private Scalr.Mode mode = Scalr.Mode.FIT_TO_WIDTH;
		
		private BufferedImageOp imageOp = Scalr.OP_ANTIALIAS;
		
		public Scalr.Method getMethod() {
			return method;
		}

		public ScalrConfig setMethod(Scalr.Method method) {
			this.method = method;
			return this;
		}

		public Scalr.Mode getMode() {
			return mode;
		}

		public ScalrConfig setMode(Scalr.Mode mode) {
			this.mode = mode;
			return this;
		}

		public BufferedImageOp getImageOp() {
			return imageOp;
		}

		public ScalrConfig setImageOp(BufferedImageOp imageOp) {
			this.imageOp = imageOp;
			return this;
		}
		
	}
}
