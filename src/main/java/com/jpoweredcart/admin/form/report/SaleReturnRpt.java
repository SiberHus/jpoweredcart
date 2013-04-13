package com.jpoweredcart.admin.form.report;

import java.io.Serializable;
import java.util.Date;

public class SaleReturnRpt implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Date startDate;
	
	protected Date endDate;
	
	protected int returns;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getReturns() {
		return returns;
	}

	public void setReturns(int returns) {
		this.returns = returns;
	}
	
}
