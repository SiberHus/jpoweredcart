package com.jpoweredcart.common;

import java.io.Serializable;
import java.util.Locale;

public class UserAttributes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "userAttrs";
	
	private Integer storeId;
	
	private String language; //language code
	
	private Integer languageId;
	
	private Locale locale;
	
	private String currency; //currency code
	
	private String shippingAddr;

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getShippingAddr() {
		return shippingAddr;
	}

	public void setShippingAddr(String shippingAddr) {
		this.shippingAddr = shippingAddr;
	}
	
	
//	public static class Session {
//		
//		public static final String STORE_ID = "$_store_id";//int
//		
//		public static final String LANGUAGE = "$_lang";//string
//		public static final String LANGUAGE_ID = "$_lang_id";//int
//		
//		public static final String CURRENCY_CODE = "$_currency";//string
//		
//		public static final String SHIPPING_ADDR = "$_ship_addr";//string
//		
//	}
}
