package com.jpoweredcart.common.entity.catalog;

import javax.validation.constraints.Size;

import com.jpoweredcart.common.entity.Description;

public class AttributeDesc extends Description{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer attributeId;
	
	@Size(min=3, max=64)
	protected String name;
	
	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
