package com.jpoweredcart.admin.form.report;

import java.io.Serializable;

public abstract class AbstractCustomerRpt  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer customerId;
	
	protected String customerName;//full name
	
	protected String customerGroup;
	
	protected String email;
	
	protected short status;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
}
