package com.jpoweredcart.common.entity.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VoucherHistory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id;
	
	protected Integer voucherId;
	
	protected Integer orderId;
	
	protected BigDecimal amount;
	
	protected Date dateAdded;

	/*============ Transient =============*/
	protected String customer;//customer fullName
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Integer voucherId) {
		this.voucherId = voucherId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}
