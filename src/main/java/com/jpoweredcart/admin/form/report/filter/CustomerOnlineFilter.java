package com.jpoweredcart.admin.form.report.filter;

import java.io.Serializable;

public class CustomerOnlineFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected String ip;
	
	protected String customerName;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
