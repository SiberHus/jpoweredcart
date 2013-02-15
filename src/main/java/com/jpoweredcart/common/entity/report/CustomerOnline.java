package com.jpoweredcart.common.entity.report;

import java.io.Serializable;
import java.util.Date;

public class CustomerOnline implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected String ip;
	
	protected Integer customerId;
	
	protected String url;
	
	protected String referer;
	
	/**
	 * This attribute refers to column date_added in database
	 */
	protected Date dateModified;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	
}
