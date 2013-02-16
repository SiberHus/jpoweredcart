package com.jpoweredcart.admin.model.catalog.jdbc;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jpoweredcart.admin.bean.catalog.DownloadForm;
import com.jpoweredcart.admin.model.catalog.DownloadAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Download;

public class DownloadAdminModelImpl extends BaseModel implements DownloadAdminModel{

	@Transactional
	@Override
	public void create(DownloadForm dlForm) {
		if(dlForm.getFileUpload().isEmpty()){
			throw new IllegalArgumentException("fileUpload must not be empty");
		}
		
		//TODO: use FileService instead
//		multipartFile.transferTo(realFile);
	}
	
	@Transactional
	@Override
	public void update(DownloadForm dlForm) {
		if(dlForm.getFileUpload().isEmpty()){
			
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer dlId) {
		
	}

	@Override
	public DownloadForm newForm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DownloadForm getForm(Integer dlId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Download get(Integer dlId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Download> getList(PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Download> getDescriptions(Integer dlId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	protected void validateFile(MultipartFile multipartFile){
		
		long fileSize = multipartFile.getSize();
		long maxSize = getEnvironment().getProperty("file.uploadSize", Long.class, new Long(1048576));
		double fileSizeMb = fileSize/1024/1024;
		if(fileSize >= maxSize){
			throw new IllegalArgumentException("File is bigger than the maximum allowed size: "+maxSize);
		}
	}
	
}
