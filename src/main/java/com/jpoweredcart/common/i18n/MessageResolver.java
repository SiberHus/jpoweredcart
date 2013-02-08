package com.jpoweredcart.common.i18n;

import javax.servlet.http.HttpServletRequest;

import org.thymeleaf.messageresolver.IMessageResolver;


public interface MessageResolver extends IMessageResolver {
	
	public String resolveMessage(HttpServletRequest httpRequest, String key,
			Object[] messageParameters);
	
}
