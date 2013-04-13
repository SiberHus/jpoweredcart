package com.jpoweredcart.common.system;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jpoweredcart.common.Attribute;
import com.jpoweredcart.common.service.StoreResolver;

public class UserDataInterceptor extends HandlerInterceptorAdapter {
	
	private JdbcOperations jdbcOperations;
	
	private StoreResolver storeResolver;
	
	private String languageParam;
	
	private String currencyParam;
	
	public JdbcOperations getJdbcOperations() {
		return jdbcOperations;
	}
	
	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
	public StoreResolver getStoreResolver() {
		return storeResolver;
	}

	public void setStoreResolver(StoreResolver storeResolver) {
		this.storeResolver = storeResolver;
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
		
		String language = request.getParameter(this.languageParam);
		if (language != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			localeResolver.setLocale(request, response, StringUtils.parseLocaleString(language));
		}
		
		String currency = request.getParameter(this.currencyParam);
		if(currency != null){
//			session.setAttribute(Attribute.Session.CURRENCY_CODE, storeId);
		}
		
		if(session.getAttribute(Attribute.Session.STORE_ID)==null){
			Integer storeId = storeResolver.resolve(request.getRequestURL().toString());
			session.setAttribute(Attribute.Session.STORE_ID, storeId);
		}
		
		return true;
	}
	
}
