package org.jpoweredcart.admin.entity.catalog;

import java.io.Serializable;

public class ProductAttribute implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer productId;
	
	protected Integer attributeId;
	
	protected Integer languageId;
	
	protected String text;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(Integer attributeId) {
		this.attributeId = attributeId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
