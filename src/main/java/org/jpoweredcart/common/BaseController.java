package org.jpoweredcart.common;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jpoweredcart.common.i18n.MessageResolver;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;


public class BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private MessageResolver messageResolver;
	
	@Inject
	private ConversionService conversionService;
	
	@Inject
	private SettingService settingService;
	
	protected String message(HttpServletRequest request, String key, Object... args){
		
		return messageResolver.resolveMessage(request, key, args);
	}
	
	protected String uri(String page){
		
		return servletContext.getContextPath()+page;
	}
	
	protected PageParam createPageParam(HttpServletRequest request){
		String sortKey = request.getParameter("sort");
		String orderDir = request.getParameter("order");
		String pageStr = request.getParameter("page");
		int page = 0;
		if(!StringUtils.isBlank(pageStr)){
			page = conversionService.convert(pageStr, Integer.class);
		}
		int limit = settingService.getConfig(SettingKey.CFG_ADMIN_LIMIT, Integer.class);
		int start = (page - 1) * limit;
		
		PageParam pageParam = new PageParam();
		pageParam.setSortKey(sortKey);
		pageParam.setOrderDir(orderDir);
		pageParam.setPage(page);
		pageParam.setStart(start);
		pageParam.setLimit(limit);
		return pageParam;
	}

	protected ServletContext getServletContext() {
		return servletContext;
	}

	protected MessageResolver getMessageResolver() {
		return messageResolver;
	}

	protected ConversionService getConversionService() {
		return conversionService;
	}
	
	protected SettingService getSettingService() {
		return settingService;
	}
	
}
