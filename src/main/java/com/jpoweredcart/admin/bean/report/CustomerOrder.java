package com.jpoweredcart.admin.bean.report;

import java.math.BigDecimal;

public class CustomerOrder extends AbstractCustomerRpt {
	
	private static final long serialVersionUID = 1L;
	
	protected int orders;//number of orders
	
	protected int products;//quantity of products
	
	protected BigDecimal total; //total price

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getProducts() {
		return products;
	}

	public void setProducts(int products) {
		this.products = products;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
