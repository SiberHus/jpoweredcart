package com.jpoweredcart.admin.bean.sale.filter;

import java.io.Serializable;
import java.util.Date;

public class TotalOrdersFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer orderStatusId;
	
	protected Integer orderId;
	
	protected String customer;
	
	protected Date dateAdded;
	
	protected Integer total;

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}