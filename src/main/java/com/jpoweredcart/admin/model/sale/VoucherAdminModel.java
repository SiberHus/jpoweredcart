package com.jpoweredcart.admin.model.sale;

import java.util.List;
import java.util.Map;

import com.jpoweredcart.admin.form.sale.VoucherForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Voucher;
import com.jpoweredcart.common.entity.sale.VoucherHistory;

public interface VoucherAdminModel {
	
	public void create(VoucherForm voucherForm);
	
	public void update(VoucherForm voucherForm);
	
	public void delete(Integer voucherId);
	
	public VoucherForm newForm();
	
	public VoucherForm getForm(Integer voucherId);
	
	public Voucher get(Integer voucherId, Class<? extends Voucher> clazz);
	
	public Voucher getOneByCode(String code);
	
	public List<Voucher> getList(PageParam pageParam);
	
	public void sendVoucher(Integer voucherId, Map<String, String> messageMap);
	
	public int getTotal();
	
	public int getTotalByThemeId(Integer voucherThemeId);
	
	public List<VoucherHistory> getHistories(Integer voucherId, int start, int limit);
	
	public int getTotalHistories(Integer voucherId);
	
}
