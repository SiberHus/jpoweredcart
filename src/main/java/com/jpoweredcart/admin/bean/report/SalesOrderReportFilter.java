package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;
import java.util.Date;

public class SalesOrderReportFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected Date dateStart;
	
	protected Date dateEnd;
	
	protected String group;
	
	protected Integer orderStatusId;
	
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
	
}
