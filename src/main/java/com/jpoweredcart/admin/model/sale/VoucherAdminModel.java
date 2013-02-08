package com.jpoweredcart.admin.model.sale;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Voucher;
import com.jpoweredcart.common.entity.sale.VoucherHistory;

public interface VoucherAdminModel {
	
	public void create(Voucher voucher);
	
	public void update(Voucher voucher);
	
	public void delete(Integer voucherId);
	
	public Voucher get(Integer voucherId);
	
	public Voucher getOneByCode(String code);
	
	public List<Voucher> getList(PageParam pageParam);
	
	public void sendVoucher(Integer voucherId);
	
	public int getTotal();
	
	public int getTotalByThemeId(Integer voucherThemeId);
	
	public List<VoucherHistory> getVoucherHistories(Integer voucherId, int start, int limit);
	
	public int getTotalVoucherHistories(Integer voucherId);
	
}
