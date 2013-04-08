package com.jpoweredcart.common.service.template;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ViewResolver;

import com.jpoweredcart.common.web.mock.MockHttpServletRequest;
import com.jpoweredcart.common.web.mock.MockHttpServletResponse;

public class SpringWebTemplateService implements TemplateService {
	
	private ViewResolver viewResolver;
	
	public ViewResolver getViewResolver() {
		return viewResolver;
	}
	
	public void setViewResolver(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	@Override
	public String renderTemplate(String path, Map<String, Object> model,
			HttpServletRequest request){
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		try {
			
			viewResolver.resolveViewName(path, Locale.US)
				.render(model, request, response);
			
			return response.getContentAsString();
			
		} catch (Exception e) {
			throw new TemplateRenderException(e);
		}
	}
	
	@Override
	public String renderTemplate(String path, Map<String, Object> model){
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI(path);
		
		return renderTemplate(path, model, request);
	}
	
	
}
