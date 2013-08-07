package com.jpoweredcart.common.system;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jpoweredcart.common.UserAttributes;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.service.CurrencyService;
import com.jpoweredcart.common.service.LanguageService;
import com.jpoweredcart.common.service.StoreResolver;
import com.jpoweredcart.common.system.setting.DefaultSettings;
import com.jpoweredcart.common.system.setting.SettingKey;
import com.jpoweredcart.common.system.setting.SettingService;

public class UserDataInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDataInterceptor.class);
	
	private JdbcOperations jdbcOperations;
	
	private SettingService settingService;
	
	private LanguageService languageService;
	
	private StoreResolver storeResolver;
	
	private CurrencyService currencyService;
	
	private String languageParam;
	
	private String currencyParam;
	
	public JdbcOperations getJdbcOperations() {
		return jdbcOperations;
	}
	
	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
	public SettingService getSettingService() {
		return settingService;
	}
	
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	
	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public StoreResolver getStoreResolver() {
		return storeResolver;
	}

	public void setStoreResolver(StoreResolver storeResolver) {
		this.storeResolver = storeResolver;
	}
	
	public CurrencyService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public String getLanguageParam() {
		return languageParam;
	}

	public void setLanguageParam(String languageParam) {
		this.languageParam = languageParam;
	}

	public String getCurrencyParam() {
		return currencyParam;
	}
	
	public void setCurrencyParam(String currencyParam) {
		this.currencyParam = currencyParam;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		
		HttpSession session = request.getSession();
		
		UserAttributes userAttrs = (UserAttributes)session.getAttribute(UserAttributes.NAME);
		if(userAttrs==null){
			userAttrs = new UserAttributes();
			userAttrs.setStoreId(storeResolver.getStoreId(
					request.getRequestURL().toString()));
			userAttrs.setLanguageId(DefaultSettings.LANGUAGE_ID);
			userAttrs.setCurrencyCode(currencyService.getDefaultCurrencyCode());
			session.setAttribute(UserAttributes.NAME, userAttrs);
		}
		
		String langCode = request.getParameter(this.languageParam);
		if (langCode != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			
			Locale locale = StringUtils.parseLocaleString(langCode);
			localeResolver.setLocale(request, response, locale);
			
			Language language = languageService.getByCode(langCode);
			if(language!=null){
				userAttrs.setLanguageId(language.getId());
				locale = language.getLocale();
			}else{
				logger.warn("Language id not found for code: {}", langCode);
			}
		}
		
		String currencyCode = request.getParameter(this.currencyParam);
		if(currencyCode != null){
			if(!currencyService.has(currencyCode)){ 
				currencyCode = settingService.getConfig(
						userAttrs.getStoreId(), SettingKey.CFG_CURRENCY);
			}
			userAttrs.setCurrencyCode(currencyCode);
		}
		
		return true;
	}
	
}
