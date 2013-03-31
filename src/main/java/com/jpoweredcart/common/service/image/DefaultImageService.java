package com.jpoweredcart.common.service.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;

public class DefaultImageService extends AbstractImageService {
	
	private String imageDir;
	
	private String thumbnailDir;
	
	@Override
	public void storeImage(String imgPath, String newName) {
		
	}
	
	@Override
	public void createThumbnail(String imgPath, int newWidth,
			int newHeight) throws IOException {
		
		String thumbPath = FilenameUtils.getBaseName(imgPath)+
				"_"+newWidth+"x"+newHeight+FilenameUtils.getExtension(imgPath);
		File imgFile = new File(getImageDir()+imgPath);
		File thumbFile = new File(getThumbnailDir()+thumbPath);
		resizeImage(imgFile, newWidth, newHeight, thumbFile);
		
	}
	
	
	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = checkDirectory(imageDir);
	}
	
	public String getThumbnailDir() {
		return thumbnailDir;
	}

	public void setThumbnailDir(String thumbnailDir) {
		this.thumbnailDir = thumbnailDir;
	}
	
	protected String checkDirectory(String dir){
		if(StringUtils.isBlank(dir)){
			throw new IllegalArgumentException("directory couldn't be empty");
		}else{
			File dirFile = new File(dir);
			if(!dirFile.exists()){
				if(!dirFile.mkdir()){
					throw new IllegalArgumentException("directory: '"+dir
							+"' does not exist and the system could not create it fot you.");
				}
			}
		}
		if( dir!=null && !(dir.endsWith("/") || dir.endsWith("\\")) ){
			return dir+File.separator;
		}
		
		return dir;
	}
	
	public static void main(String[] args) throws Exception {
		String baseDir = "D:/jobs/SiberHus/Workspaces/Java/Products/jpoweredcart/jpoweredcart/src/main/webapp/resources/image/data/";
		DefaultImageService imgService = new DefaultImageService();
		imgService.setImageDir(baseDir);
		
		File imgFile = new File(baseDir+"htc_touch_hd_1.jpg");
		
		BufferedImage originalImage = ImageIO.read(imgFile);
		BufferedImage thumbnail =
			Scalr.resize(originalImage, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
			100, 100, Scalr.OP_ANTIALIAS);
		File newImageFile = new File(baseDir+"logo-100x100.png");
		ImageIO.write(thumbnail, ImageType.PNG.name(), newImageFile);
	}
}
