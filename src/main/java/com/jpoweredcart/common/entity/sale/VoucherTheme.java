package com.jpoweredcart.common.entity.sale;

import java.io.Serializable;

public class VoucherTheme implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String image;
	
	//=================== Transient properties ====================//
	protected String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
