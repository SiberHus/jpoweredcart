package org.jpoweredcart.common.exception;

import org.springframework.ui.ModelMap;

public interface TemplateAware {

	public void setModelMap(ModelMap model);
	
	public String getLanguage();
	
}
