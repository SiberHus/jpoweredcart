package com.jpoweredcart.common;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;

import com.jpoweredcart.common.i18n.MessageResolver;
import com.jpoweredcart.common.system.setting.SettingKey;
import com.jpoweredcart.common.system.setting.SettingService;
import com.jpoweredcart.common.view.JQueryDateFormatTranslator;


public abstract class BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private MessageResolver messageResolver;
	
	@Inject
	private ConversionService conversionService;
	
	@Inject
	private SettingService settingService;
	
	/**
	 * Shorthand method for messageResolver.resolveMessage(request, key, args);
	 * 
	 * @param request
	 * @param key
	 * @param args
	 * @return
	 */
	protected String message(HttpServletRequest request, String key, Object... args){
		
		return messageResolver.resolveMessage(request, key, args);
	}
	
	/**
	 * 
	 * @param page
	 * @return
	 */
	protected String uri(String page){
		
		return servletContext.getContextPath()+page;
	}
	
	/**
	 * Create PageParam object from HttpServletRequest
	 * 
	 * @param request
	 * @return
	 */
	protected PageParam createPageParam(HttpServletRequest request){
		String sortKey = request.getParameter("sort");
		String orderDir = request.getParameter("order");
		String pageStr = request.getParameter("page");
		int page = 0;
		if(StringUtils.isNotBlank(pageStr)){
			page = conversionService.convert(pageStr, Integer.class);
		}
		int limit = settingService.getConfig(SettingKey.CFG_ADMIN_LIMIT, Integer.class);
		int start = (page - 1) * limit;
		
		PageParam pageParam = new PageParam();
		if(StringUtils.isNotBlank(sortKey)){
			pageParam.addOrder(sortKey, orderDir);
		}
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
	
	protected void addJsDateFormatAttribute(Model model, HttpServletRequest request){
		String jsDateFormat = JQueryDateFormatTranslator.INSTANCE
				.translate(message(request, "date.formatShort"));
		model.addAttribute("jsDateFormat", jsDateFormat);
	}
	
	/**
	 * The default data binder setup.
	 * Don't activate @InitBinder in the base class
	 * Some controllers may not need it. So, it's not necessary to load it every time.
	 * If some controllers want to use custom data binder, those controllers may override
	 * this method and annotate it with @InitBinder annotation.
	 * 
	 * @param binder
	 * @param request
	 */
//	@InitBinder
	protected void initBinder(WebDataBinder binder, final HttpServletRequest request) {
		
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
			@Override
			public String getAsText() {
				return createDateFormat().format(getValue());
			}
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				try{
					if(StringUtils.isNotBlank(text)){
						setValue(createDateFormat().parse(text));
					}
				}catch(ParseException e){
					setValue(null);
				}
			}
			public DateFormat createDateFormat(){
				return new SimpleDateFormat(message(request, "date.formatShort"));
			}
		});
	}
	
	
	
}
