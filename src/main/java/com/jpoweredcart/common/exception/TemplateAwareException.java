package org.jpoweredcart.common.exception;

import org.springframework.ui.ModelMap;

public class TemplateAwareException extends RuntimeException implements TemplateAware {
	
	private static final long serialVersionUID = 1L;
	
	private ModelMap model;
	
	private String language;
	
	public TemplateAwareException(){}
	
	public TemplateAwareException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateAwareException(String message) {
		super(message);
	}

	public TemplateAwareException(Throwable cause) {
		super(cause);
	}

	@Override
	public void setModelMap(ModelMap model) {
		this.model = model;
	}
	
	public TemplateAwareException addAttribute(String name, Object value){
		model.addAttribute(name, value);
		return this;
	}
	
	@Override
	public String getLanguage() {
		return language;
	}
	
	public TemplateAwareException setLanguage(String language){
		this.language = language;
		return this;
	}
	
}
