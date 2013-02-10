package com.jpoweredcart.admin.bean.report.filter;

public class DateRangeWithStatusFilter extends DateRangeFilter {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer statusId;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
}
