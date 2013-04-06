package com.jpoweredcart.admin.bean.sale.filter;

import java.io.Serializable;
import java.util.Date;

public class CustomerFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String customerName;
	
	protected String email;
	
	protected Integer customerGroupId;
	
	protected Short status;
	
	protected Short approved;
	
	protected String ip;
	
	protected Date dateAdded;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCustomerGroupId() {
		return customerGroupId;
	}

	public void setCustomerGroupId(Integer customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getApproved() {
		return approved;
	}

	public void setApproved(Short approved) {
		this.approved = approved;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
}
