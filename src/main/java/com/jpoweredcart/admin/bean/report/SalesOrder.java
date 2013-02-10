package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;
import java.util.Date;

public class SalesOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Date dateStart;
	
	protected Date dateEnd;
	
	protected int orders;
	
	protected int products;
	
	protected int tax;
	
	protected int total;

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

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

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
