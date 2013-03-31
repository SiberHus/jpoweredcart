package com.jpoweredcart.common.service.email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jpoweredcart.common.service.setting.DefaultSettings;


public class EmailMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer storeId = DefaultSettings.STORE_ID;
	
	protected String to;
	
	protected String from;
	
	protected String senderName;
	
	protected String subject;
	
	protected String bodyHtml;
	
	protected String bodyText;
	
	protected Map<String, String> variables;
	
	
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBodyHtml() {
		return bodyHtml;
	}

	public void setBodyHtml(String bodyHtml) {
		this.bodyHtml = bodyHtml;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public Map<String, String> getVariables() {
		return variables;
	}

	public void setVariable(String name, String value) {
		if(this.variables==null){
			this.variables = new HashMap<String, String>();
		}
		this.variables.put(name, value);
	}
	
}
