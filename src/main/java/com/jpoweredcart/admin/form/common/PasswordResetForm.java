package com.jpoweredcart.admin.form.common;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordResetForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected String code;
	
	@NotBlank @Size(min=5, max=20)
	protected String password;
	
	protected String confirm;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
}
