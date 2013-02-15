package com.jpoweredcart.admin.bean.report;

import java.math.BigDecimal;

public class CustomerReward extends AbstractCustomerRpt {
	
	private static final long serialVersionUID = 1L;
	
	protected int points;
	
	protected int orders;
	
	protected BigDecimal total;

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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
