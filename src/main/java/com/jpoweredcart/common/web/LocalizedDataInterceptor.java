package com.jpoweredcart.common.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

public class LocalizedDataInterceptor extends LocaleChangeInterceptor {
	
	private JdbcOperations jdbcOperations;
	
	private String currencyParam;
	
	public JdbcOperations getJdbcOperations() {
		return jdbcOperations;
	}

	public void setJdbcOperations(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
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
		
		String currency = request.getParameter(this.currencyParam);
		if(currency != null){
			
		}
		return super.preHandle(request, response, handler);
	}
}
