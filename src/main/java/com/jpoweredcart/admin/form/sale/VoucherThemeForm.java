package com.jpoweredcart.admin.form.sale;

import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.VoucherThemeDesc;

public class VoucherThemeForm extends VoucherTheme {
	
	private static final long serialVersionUID = 1L;

	@Valid
	protected List<VoucherThemeDesc> descs = new AutoPopulatingList<VoucherThemeDesc>(VoucherThemeDesc.class);

	public List<VoucherThemeDesc> getDescs() {
		return descs;
	}

	public void setDescs(List<VoucherThemeDesc> descs) {
		this.descs = new AutoPopulatingList<VoucherThemeDesc>(descs, VoucherThemeDesc.class);
	}
	
}
