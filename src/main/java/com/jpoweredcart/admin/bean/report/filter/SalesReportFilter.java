package com.jpoweredcart.admin.bean.report.filter;


public class SalesReportFilter extends DateRangeWithStatusFilter {
	
	private static final long serialVersionUID = 1L;

	protected String group;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
	
}
