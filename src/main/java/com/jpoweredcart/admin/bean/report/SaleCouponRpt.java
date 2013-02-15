package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleCouponRpt implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Integer couponId;
	
	protected String name;
	
	protected String code;
	
	protected int orders;
	
	protected BigDecimal total;
	
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
