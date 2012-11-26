package org.jpoweredcart.admin.entity.catalog;

import java.io.Serializable;

public class ProductFilter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer productId;
	
	protected Integer filterValueId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getFilterValueId() {
		return filterValueId;
	}

	public void setFilterValueId(Integer filterValueId) {
		this.filterValueId = filterValueId;
	}
	
}
