package org.jpoweredcart.common.entity.catalog;

import java.io.Serializable;

public class ProductOption implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer productId;
	
	protected Integer optionId;
	
	protected String optionValue;
	
	protected boolean required;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	
}
