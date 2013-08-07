package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.admin.form.sale.VoucherThemeForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.VoucherThemeDesc;

public interface VoucherThemeAdminModel {

	public void create(VoucherThemeForm vtForm);

	public void update(VoucherThemeForm vtForm);

	public void delete(Integer vtId);

	public VoucherThemeForm newForm();

	public VoucherThemeForm getForm(Integer vtId);

	public VoucherTheme get(Integer vtId, Class<? extends VoucherTheme> clazz);

	public List<VoucherTheme> getList(PageParam pageParam);

	public List<VoucherThemeDesc> getDescriptions(Integer vtId);

	public int getTotal();
}
