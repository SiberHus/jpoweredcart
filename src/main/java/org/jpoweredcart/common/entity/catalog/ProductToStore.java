package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class ProductToStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer productId;
	
	protected Integer storeId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
}
