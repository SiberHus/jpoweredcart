package com.jpoweredcart.admin.form.report;

import com.jpoweredcart.common.entity.report.CustomerOnline;


public class CustomerOnlineRpt extends CustomerOnline {
	
	private static final long serialVersionUID = 1L;
	
	protected String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}
