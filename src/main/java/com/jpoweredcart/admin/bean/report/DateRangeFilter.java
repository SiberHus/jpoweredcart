package com.jpoweredcart.admin.bean.report;

import java.io.Serializable;
import java.util.Date;

public class DateRangeFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Date dateStart;
	
	protected Date dateEnd;
	
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
	
}
