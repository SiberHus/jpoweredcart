package com.jpoweredcart.admin.form.sale;

import com.jpoweredcart.common.entity.sale.Affiliate;

public class AffiliateForm extends Affiliate {
	
	private static final long serialVersionUID = 1L;

	protected String confirm;//confirm password

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
}
