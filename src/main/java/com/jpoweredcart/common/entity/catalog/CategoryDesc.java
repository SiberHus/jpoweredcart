package com.jpoweredcart.common.entity.catalog;

import javax.validation.constraints.Size;

import com.jpoweredcart.common.entity.Description;

public class CategoryDesc extends Description {

	private static final long serialVersionUID = 1L;
	
	protected Integer categoryId;
	
	@Size(min=2, max=255)
	protected String name;
	
	protected String description;
	
	protected String metaDescription;
	
	protected String metaKeyword;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}
	
}
