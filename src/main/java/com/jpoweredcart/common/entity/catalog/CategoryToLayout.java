package com.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class CategoryToLayout  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer categoryId;
	
	protected Integer storeId;
	
	protected String storeName;
	
	protected Integer layoutId;
	
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
	
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
}
