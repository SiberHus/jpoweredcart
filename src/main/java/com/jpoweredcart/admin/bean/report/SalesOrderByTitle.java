package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;
import java.util.Date;

public class SalesOrderByTitle implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Date dateStart;
	
	protected Date dateEnd;
	
	protected String title;
	
	protected int orders;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
