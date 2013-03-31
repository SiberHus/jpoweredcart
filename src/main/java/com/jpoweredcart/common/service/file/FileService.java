package com.jpoweredcart.common.service.file;

import java.io.FileFilter;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	
	public boolean isDirectory(String path);
	
	public boolean exists(String path);
	
	public List<FileInfo> getFiles(String directory, FileFilter filter);
	
	public List<DirectoryInfo> getDirectories(String directory);
	
	public boolean makeDir(String directory, String name);
	
	public boolean delete(String path);
	
	public boolean move(String from, String to);
	
	public boolean copy(String path, String name);
	
	public List<String> getFolders();
	
	public boolean rename(String from, String to);
	
	public boolean upload(MultipartFile multipartFile, String fileName);
	
	public String ensureEndingSlash(String path);
	
}
