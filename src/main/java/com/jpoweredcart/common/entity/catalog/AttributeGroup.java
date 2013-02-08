package com.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class AttributeGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String name;
	
	protected int sortOrder;

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

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
