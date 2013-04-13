package com.jpoweredcart.common.service;

import java.math.BigDecimal;
import java.util.Locale;

public interface CurrencyService {
	
	public void updateCurrencyDatabase() throws Exception;
	
	public String getDefaultCurrencyCode();
	
	public Integer getId(String code);
	
	public BigDecimal getValue(String code);
	
	public boolean has(String code);
	
	public String format(Number value, String code, Locale locale);
	
	public BigDecimal convert(BigDecimal value, String fromCode, String toCode);
	
	public String getSymbolLeft(String code);
	
	public String getSymbolRight(String code);
	
	public Integer getDecimalPlace(String code);
	
}
