package com.jpoweredcart.common.entity.setting;

import java.io.Serializable;

public class Extension implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String type;
	
	protected String code;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
