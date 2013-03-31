package com.jpoweredcart.common.service.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


public class DefaultFileService implements FileService {

	private String baseDir;
	
	private FileFilter dirFilter = new FileFilters.DirectoryFilter();
	
	public void setBaseDir(String baseDir){
		if(StringUtils.isBlank(baseDir)){
			throw new IllegalArgumentException("baseDir cannot be empty");
		}
		File baseDirFile = new File(baseDir);
		if(!baseDirFile.exists()){
			throw new IllegalArgumentException("baseDir doesn't exist: "+baseDir);
		}
		this.baseDir = ensureEndingSlash(baseDir);
	}
	
	@Override
	public boolean isDirectory(String path){
		String absPath = ensureEndingSlash(baseDir+path);
		File dirFile = new File(absPath);
		return dirFile.isDirectory();
	}
	
	@Override
	public boolean exists(String path){
		String absPath = ensureEndingSlash(baseDir+path);
		File dirFile = new File(absPath);
		return dirFile.exists();
	}
	
	@Override
	public List<FileInfo> getFiles(String directory, FileFilter filter) {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		String absPath = ensureEndingSlash(baseDir+directory);
		File dirFile = new File(absPath);
		if(!dirFile.isDirectory()){
			throw new IllegalArgumentException("directory is not valid: "+absPath);
		}
		String suffixes[] = new String[]{"B","KB","MB","GB","TB","PB","EB","ZB","YB"};
		File files[] = filter!=null?dirFile.listFiles(filter):dirFile.listFiles();
		for(File file: files){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFilename(file.getName());
			
			String path = absPath.substring(baseDir.length());
			if(StringUtils.isNotEmpty(path)) path += File.separator;
			path += file.getName();
			fileInfo.setFile(path);
			
			double fileSize = (double)file.length();
			int i = 0;
			while( (fileSize/1024) > 1.0){
				fileSize = fileSize / 1024;
				i++;
			}
			DecimalFormat dfmt = new DecimalFormat("#.00");
			fileInfo.setSize(dfmt.format(fileSize)+suffixes[i]);
			fileInfoList.add(fileInfo);
		}
		return fileInfoList;
	}
	
	@Override
	public List<DirectoryInfo> getDirectories(String directory) {
		List<DirectoryInfo> dirInfoList = new ArrayList<DirectoryInfo>();
		String absPath = ensureEndingSlash(baseDir+directory);
		File dirFile = new File(absPath);
		if(!dirFile.isDirectory()){
			throw new IllegalArgumentException("directory is not valid: "+absPath);
		}
		File files[] = dirFile.listFiles(dirFilter);
		for(File file: files){
			DirectoryInfo dirInfo = new DirectoryInfo();
			dirInfo.setData(file.getName());
			String path = absPath.substring(baseDir.length());
			if(StringUtils.isNotEmpty(path)) path += File.separator;
			path += file.getName();
			dirInfo.addAttribute("directory", encodePath(path));
			if(file.listFiles(dirFilter).length!=0){
				dirInfo.setChildren(" ");
			}
			dirInfoList.add(dirInfo);
		}
		return dirInfoList;
	}
	
	@Override
	public boolean makeDir(String directory, String name) {
		File dirFile = new File(baseDir+ensureEndingSlash(directory)+name);
		System.out.println(dirFile.getAbsolutePath());
		if(dirFile.mkdir()){
			dirFile.setReadable(true);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean delete(String path) {
		File file = new File(baseDir+path);
		return FileUtils.deleteQuietly(file);
	}

	@Override
	public boolean move(String from, String to) {
		File fromFile = new File(baseDir+from);
		File toFile = new File(baseDir+to);
		try{
			FileUtils.moveToDirectory(fromFile, toFile, false);
		}catch(IOException e){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean copy(String path, String name) {
		File fromFile = new File(baseDir+path);
		String newFileExt = FilenameUtils.getExtension(name);
		if(newFileExt.equals("")){
			name = name+"."+FilenameUtils.getExtension(path);
		}
		File toFile = new File(fromFile.getParent()+File.separator+name);
		try{
			FileUtils.copyFile(fromFile, toFile, false);
		}catch(IOException e){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean rename(String path, String name) {
		File fromFile = new File(baseDir+path);
		String newFileExt = FilenameUtils.getExtension(name);
		if(newFileExt.equals("")){
			name = name+"."+FilenameUtils.getExtension(path);
		}
		File toFile = new File(fromFile.getParent()+File.separator+name);
		try{
			FileUtils.moveFile(fromFile, toFile);
		}catch(IOException e){
			return false;
		}
		return true;
	}

	@Override
	public boolean upload(MultipartFile multipartFile, String fileName) {
		try{
			multipartFile.transferTo(new File(baseDir+fileName));
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
	@Override
	public String ensureEndingSlash(String path){
		if (path == null) {
			throw new IllegalArgumentException("path cannot be null");
		}
		if(!(path.endsWith("/") || path.endsWith("\\"))){
			return path+File.separator;
		}
		return path;
	}
	
	private String encodePath(String path){
//		try {
//			return URLEncoder.encode(path,"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			throw new RuntimeException(e);
//		}
//		return StringEscapeUtils.escapeHtml4(path);
		return path;
	}
	
}
