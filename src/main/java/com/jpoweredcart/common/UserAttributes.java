package com.jpoweredcart.common;

import java.io.Serializable;

public class UserAttributes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "userAttrs";
	
	private Integer storeId;
	
	private Integer languageId;
	
	private String currencyCode;
	
	private String shippingAddr;
	
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	public Integer getLanguageId() {
		return languageId;
	}
	
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getShippingAddr() {
		return shippingAddr;
	}

	public void setShippingAddr(String shippingAddr) {
		this.shippingAddr = shippingAddr;
	}
	
}
