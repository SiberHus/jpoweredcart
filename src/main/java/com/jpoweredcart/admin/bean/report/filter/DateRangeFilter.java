package com.jpoweredcart.admin.bean.report.filter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class DateRangeFilter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	protected Date dateStart;
	
	protected Date dateEnd;
	
	public void setDefaultDateRange(){
		if(getDateStart()==null){
			Calendar current = Calendar.getInstance();
			current.set(Calendar.DAY_OF_MONTH, 1);
			setDateStart(current.getTime());
		}
		if(getDateEnd()==null){
			setDateEnd(new Date());
		}
	}
	
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
