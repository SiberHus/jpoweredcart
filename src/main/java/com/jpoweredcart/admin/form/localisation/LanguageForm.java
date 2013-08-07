package com.jpoweredcart.admin.form.localisation;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.StringUtils;

import com.jpoweredcart.common.entity.localisation.Language;

public class LanguageForm extends Language {
	
	private static final long serialVersionUID = 1L;
	
	public void setLocale(String locale){
		this.locale = StringUtils.parseLocaleString(locale);
	}
	
	public String getLocaleAsString(){
		return ObjectUtils.toString(this.locale);
	}
	
}
