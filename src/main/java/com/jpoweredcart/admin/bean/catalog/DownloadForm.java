package com.jpoweredcart.admin.bean.catalog;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;
import org.springframework.web.multipart.MultipartFile;

import com.jpoweredcart.common.entity.catalog.Download;
import com.jpoweredcart.common.entity.catalog.DownloadDesc;

public class DownloadForm extends Download {
	
	private static final long serialVersionUID = 1L;

	protected MultipartFile fileUpload;
	
	@Valid
	protected List<DownloadDesc> descs = new AutoPopulatingList<DownloadDesc>(DownloadDesc.class);
	
	public MultipartFile getFileUpload() {
		return fileUpload;
	}
	
	public void setFileUpload(MultipartFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public List<DownloadDesc> getDescs() {
		return descs;
	}
	
	public void setDescs(List<DownloadDesc> descs) {
		this.descs = descs = new AutoPopulatingList<DownloadDesc>(descs, DownloadDesc.class);
	}
}
