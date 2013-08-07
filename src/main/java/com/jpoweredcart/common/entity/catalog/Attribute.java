package com.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class Attribute implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected String name;
	
	protected Integer attributeGroupId;
	
	protected String attributeGroupName;
	
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

	public Integer getAttributeGroupId() {
		return attributeGroupId;
	}

	public void setAttributeGroupId(Integer attributeGroupId) {
		this.attributeGroupId = attributeGroupId;
	}
	
	public String getAttributeGroupName() {
		return attributeGroupName;
	}

	public void setAttributeGroupName(String attributeGroupName) {
		this.attributeGroupName = attributeGroupName;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
