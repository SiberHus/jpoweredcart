package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductPurchased implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	protected String model;
	
	protected int quantity;
	
	protected BigDecimal total;
	
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
