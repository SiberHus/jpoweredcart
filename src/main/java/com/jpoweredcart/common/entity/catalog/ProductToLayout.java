package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class ProductToLayout implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer productId;
	
	protected Integer storeId;
	
	protected Integer layoutId;

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

	public Integer getLayoutId() {
		return layoutId;
	}

	public void setLayoutId(Integer layoutId) {
		this.layoutId = layoutId;
	}
	
}
