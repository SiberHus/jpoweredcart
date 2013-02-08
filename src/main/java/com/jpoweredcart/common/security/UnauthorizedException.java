package com.jpoweredcart.common.security;

import com.jpoweredcart.common.exception.TemplateAwareException;

public class UnauthorizedException extends TemplateAwareException {
	
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedException(){}
	
	public UnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnauthorizedException(String message) {
		super(message);
	}

	public UnauthorizedException(Throwable cause) {
		super(cause);
	}
	
}
