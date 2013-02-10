package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;

public class ProductsPurchased implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String name;
	
	protected String model;
	
	protected int quantity;
	
	protected int total;
	
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
