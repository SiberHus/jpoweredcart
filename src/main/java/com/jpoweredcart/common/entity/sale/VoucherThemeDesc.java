package com.jpoweredcart.common.entity.sale;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.jpoweredcart.common.entity.Description;

public class VoucherThemeDesc extends Description {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer voucherThemeId;
	
	@NotBlank @Size(min=3, max=32)
	protected String name;

	public Integer getVoucherThemeId() {
		return voucherThemeId;
	}

	public void setVoucherThemeId(Integer voucherThemeId) {
		this.voucherThemeId = voucherThemeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
