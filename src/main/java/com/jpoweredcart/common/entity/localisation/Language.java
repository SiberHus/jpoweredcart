package com.jpoweredcart.common.entity.localisation;

import java.io.Serializable;
import java.util.Locale;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


public class Language implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	@NotBlank @Size(min=3, max=32)
	protected String name;
	
	@NotBlank @Size(min=2)
	protected String code;
	
	@NotBlank
	protected Locale locale;
	
	@NotBlank @Size(min=3, max=32)
	protected String image;
	
	@NotBlank
	protected String directory;
	
	@NotBlank
	protected String filename;
	
	protected int sortOrder = 0;
	
	protected short status = 1;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	
	
}
