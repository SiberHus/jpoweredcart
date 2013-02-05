package org.jpoweredcart.admin.model.sale;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.sale.Voucher;
import org.jpoweredcart.common.entity.sale.VoucherHistory;

public interface VoucherAdminModel {
	
	public void addVoucher(Voucher voucher);
	
	public void updateVoucher(Voucher voucher);
	
	public void saveVoucher(Voucher voucher);
	
	public void deleteVoucher(Integer voucherId);
	
	public Voucher getVoucher(Integer voucherId);
	
	public Voucher getVoucherByCode(String code);
	
	public List<Voucher> getVouchers(PageParam pageParam);
	
	public void sendVoucher(Integer voucherId);
	
	public int getTotalVouchers();
	
	public int getTotalVouchersByThemeId(Integer voucherThemeId);
	
	public List<VoucherHistory> getVoucherHistories(Integer voucherId, int start, int limit);
	
	public int getTotalVoucherHistories(Integer voucherId);
	
}
