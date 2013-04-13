package com.jpoweredcart.common.entity.design;

import java.io.Serializable;

public class Banner implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String name;

	protected int status;
	
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
