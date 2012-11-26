package org.jpoweredcart.common.exception;

import org.jpoweredcart.common.i18n.ThymeleafMessageResolver;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;


public class TemplateMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex) {
		logger.debug(ex.getMessage(), ex);
		ModelAndView mv = super.getModelAndView(viewName, ex);
		ModelMap model = mv.getModelMap();
		if(ex instanceof TemplateAware){
			TemplateAware ta = (TemplateAware)ex;
			ta.setModelMap(model);
			if(ta.getLanguage()!=null){
				ThymeleafMessageResolver.setMessagePath(model, ta.getLanguage());
			}
		}
		
		return mv;
	}
}
