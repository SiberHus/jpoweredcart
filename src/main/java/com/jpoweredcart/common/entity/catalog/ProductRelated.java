package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class ProductRelated implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer productId;
	
	protected Integer relatedId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Integer relatedId) {
		this.relatedId = relatedId;
	}
	
}
