package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class CategoryToStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer categoryId;
	
	protected Integer storeId;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
}
