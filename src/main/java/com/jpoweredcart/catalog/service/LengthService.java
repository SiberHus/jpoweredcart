package com.jpoweredcart.catalog.service;

import java.math.BigDecimal;

public interface LengthService {
	
	public BigDecimal convert(BigDecimal value, Integer languageId,  
			Integer fromClassId, Integer toClassId);
	
	public String format(Number value, Integer languageId, Integer classId);
	
	public String getUnit(Integer languageId, Integer classId);
	
}
