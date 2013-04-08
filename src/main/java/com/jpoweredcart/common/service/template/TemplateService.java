package com.jpoweredcart.common.service.template;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface TemplateService {
	
	/**
	 * Render the template within web context.
	 *  
	 * @param path
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	public String renderTemplate(String path, Map<String, Object> model,
			HttpServletRequest request) ;
	
	/**
	 * Render template from specified path. The parameters in template file will be substituted
	 * by the key-value in model object.
	 * 
	 * @param path
	 * @param model
	 * @return
	 */
	public String renderTemplate(String path, Map<String, Object> model);
	
}
