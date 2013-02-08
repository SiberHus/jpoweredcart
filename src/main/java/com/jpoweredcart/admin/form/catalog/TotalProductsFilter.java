package org.jpoweredcart.admin.form.catalog;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalProductsFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer categoryId;
	
	protected String subCategory;
	
	protected String name;
	
	protected String model;
	
	protected BigDecimal price;
	
	protected Integer quantity;
	
	protected Integer status;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
