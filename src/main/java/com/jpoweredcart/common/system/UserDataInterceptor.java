package com.jpoweredcart.common.system;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.UserAttributes;
import com.jpoweredcart.common.entity.Status;
import com.jpoweredcart.common.service.CurrencyService;
import com.jpoweredcart.common.service.StoreResolver;
import com.jpoweredcart.common.system.setting.DefaultSettings;
import com.jpoweredcart.common.system.setting.SettingKey;
import com.jpoweredcart.common.system.setting.SettingService;

public class UserDataInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDataInterceptor.class);
	
	private JdbcOperations jdbcOperations;
	
	private SettingService settingService;
	
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
			userAttrs.setStoreId(storeResolver.resolve(
					request.getRequestURL().toString()));
			userAttrs.setLanguageId(DefaultSettings.LANGUAGE_ID);
			userAttrs.setCurrency(currencyService.getDefaultCurrencyCode());
			String defaultLangCode = settingService.getConfig(
					userAttrs.getStoreId(), SettingKey.CFG_LANGUAGE);
			userAttrs.setLanguage(defaultLangCode);
			userAttrs.setLocale(new Locale(defaultLangCode));
			session.setAttribute(UserAttributes.NAME, userAttrs);
		}
		
		/* We can expect locale format here */
		String language = request.getParameter(this.languageParam);
		if (language != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			Locale locale = StringUtils.parseLocaleString(language);
			localeResolver.setLocale(request, response, locale);
			Environment env = getSettingService().getEnvironment();
			Integer languageId = null;
			String sql = "SELECT * FROM "+BaseModel.quoteTable(env, "language")+" WHERE code=? AND status=?";
			try{
				languageId = jdbcOperations.queryForObject(sql, 
					new Object[]{language, Status.ENABLED}, Integer.class);
				userAttrs.setLanguageId(languageId);
			}catch(IncorrectResultSizeDataAccessException e){
				logger.warn("Language id not found for code: {}", language);
			}
			userAttrs.setLanguage(language);
			userAttrs.setLocale(locale);
		}
		
		String currency = request.getParameter(this.currencyParam);
		if(currency != null){
			if(!currencyService.has(currency)){ 
				currency = settingService.getConfig(
						userAttrs.getStoreId(), SettingKey.CFG_CURRENCY);
			}
			userAttrs.setCurrency(currency);
		}
		
		return true;
	}
	
}
