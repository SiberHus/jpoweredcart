package com.jpoweredcart.admin.form.report.filter;


public class SaleReportFilter extends DateRangeWithStatusFilter {
	
	private static final long serialVersionUID = 1L;

	protected String group;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
}
