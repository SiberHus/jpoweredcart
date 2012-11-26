package org.jpoweredcart.admin.entity.catalog;

import java.io.Serializable;

public class CategoryToLayout  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer categoryId;
	
	protected Integer storeId;
	
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

	public Integer getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
}
