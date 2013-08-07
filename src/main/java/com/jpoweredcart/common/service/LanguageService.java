package com.jpoweredcart.common.service;

import com.jpoweredcart.common.entity.localisation.Language;

public interface LanguageService {
	
	public Language getByCode(String code);
	
	public Language getById(Integer id);
	
}
