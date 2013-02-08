package com.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class InformationToStore implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer informationId;
	
	protected Integer storeId;
	

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
	
}
