package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;

public class SalesCoupon implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Integer couponId;
	
	protected String name;
	
	protected String code;
	
	protected int orders;
	
	protected int total;
	
	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
