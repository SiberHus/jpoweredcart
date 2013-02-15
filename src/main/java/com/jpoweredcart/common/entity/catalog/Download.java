package com.jpoweredcart.common.entity.catalog;

import java.io.Serializable;
import java.util.Date;

public class Download implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String fileName;
	
	//@Transient
	protected String name;//default name
	
	protected String mask;
	
	protected int remaining;
	
	protected Date dateAdded;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
}
