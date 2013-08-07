package com.jpoweredcart.common.exception.admin;

import com.jpoweredcart.common.security.UnauthorizedException;

public class UnauthorizedAdminException extends UnauthorizedException {
	
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedAdminException(){
		setLanguage("/admin/error/permission");
	}
	
}
