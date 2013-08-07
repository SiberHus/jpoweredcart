package com.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class InformationToLayout implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer informationId;
	
	protected Integer storeId;
	
	protected String storeName;
	
	protected Integer layoutId;

	public Integer getInformationId() {
		return informationId;
	}

	public void setInformationId(Integer informationId) {
		this.informationId = informationId;
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
