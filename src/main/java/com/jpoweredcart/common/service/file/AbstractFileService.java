package com.jpoweredcart.common.service.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jpoweredcart.common.utils.PathUtils;

public abstract class AbstractFileService implements FileService {
	
	private String baseDir;
	
	private String baseUrl;
	
	public String getBaseDir(){
		return baseDir;
	}
	
	public void setBaseDir(String baseDir){
		if(StringUtils.isBlank(baseDir)){
			throw new IllegalArgumentException("baseDir cannot be empty");
		}
		File baseDirFile = new File(baseDir);
		if(!baseDirFile.exists()){
			throw new IllegalArgumentException("baseDir doesn't exist: "+baseDir);
		}
		baseDir = baseDirFile.getAbsolutePath();
		this.baseDir = PathUtils.ensureEndingFileSeparator(baseDir);
	}
	
	public String getBaseUrl() {
		return baseUrl;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = PathUtils.ensureEndingSlash(baseUrl);
	}
	
	@Override
	public String getUrl(String path) {
		return getBaseUrl()+path;
	}

	@Override
	public boolean upload(MultipartFile multipartFile, String fileName) {
		if(multipartFile.isEmpty()){
			//file cannot be empty
			return false;
		}
		try{
			multipartFile.transferTo(new File(baseDir+fileName));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	/**
	 * Generate random base name (no extension).
	 * The first part is timestamp (yyMMddHHmmss)
	 * The second part is random alphanumeric 12 chars
	 * The return format is "timestamp_random"
	 */
	@Override
	public String generateRandomName() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		String random = RandomStringUtils.randomAlphanumeric(12);
		return timestamp+random;
	}
	
	protected String encodePath(String path){
//		try {
//			return URLEncoder.encode(path,"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			throw new RuntimeException(e);
//		}
//		return StringEscapeUtils.escapeHtml4(path);
		return path;
	}
	
}
