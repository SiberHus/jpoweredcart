package com.jpoweredcart.common.entity.catalog;

import javax.validation.constraints.Size;

import com.jpoweredcart.common.entity.Description;

public class AttributeGroupDesc extends Description{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer attributeGroupId;
	
	@Size(min=3, max=64)
	protected String name;
	
	public Integer getAttributeGroupId() {
		return attributeGroupId;
	}

	public void setAttributeGroupId(Integer attributeGroupId) {
		this.attributeGroupId = attributeGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
