package com.jpoweredcart.admin.bean.sale.filter;

import java.io.Serializable;
import java.util.Date;

public class AffiliateFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String affilateName;
	
	protected String email;
	
	protected String code;
	
	//use wrapper because it's nullable
	protected Short status; 
	
	//use wrapper because it's nullable
	protected Boolean approved;
	
	protected Date dateAdded;

	public String getAffilateName() {
		return affilateName;
	}

	public void setAffilateName(String affilateName) {
		this.affilateName = affilateName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	
}
