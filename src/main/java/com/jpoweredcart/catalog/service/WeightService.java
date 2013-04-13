package com.jpoweredcart.catalog.service;

import java.math.BigDecimal;
import java.util.Locale;

public interface WeightService {
	
	public BigDecimal convert(BigDecimal value, Integer languageId, 
			Integer fromClassId, Integer toClassId);
	
	public String format(BigDecimal value, Integer languageId, Integer classId, Locale locale);
	
	public String getUnit(Integer languageId, Integer classId);
	
}
