package com.jpoweredcart.admin.bean.report;


public class SalesReportFilter extends DateRangeFilter {
	
	private static final long serialVersionUID = 1L;

	protected String group;
	
	protected Integer statusId;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
}
