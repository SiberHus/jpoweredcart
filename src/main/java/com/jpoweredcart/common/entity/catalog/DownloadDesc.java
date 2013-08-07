package com.jpoweredcart.common.entity.catalog;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.jpoweredcart.common.entity.Description;

public class DownloadDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer downloadId;
	
	@NotBlank @Size(min=3, max=64)
	protected String name;
	
	public Integer getDownloadId() {
		return downloadId;
	}

	public void setDownloadId(Integer downloadId) {
		this.downloadId = downloadId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
