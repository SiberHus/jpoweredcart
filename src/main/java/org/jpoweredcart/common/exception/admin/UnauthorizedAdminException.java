package org.jpoweredcart.common.exception.admin;

import org.jpoweredcart.common.security.UnauthorizedException;

public class UnauthorizedAdminException extends UnauthorizedException {
	
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedAdminException(){
		setLanguage("/admin/error/permission");
	}
	
}
