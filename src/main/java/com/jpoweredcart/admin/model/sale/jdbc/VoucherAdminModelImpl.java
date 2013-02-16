package com.jpoweredcart.admin.model.sale.jdbc;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.sale.VoucherAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.sale.Voucher;
import com.jpoweredcart.common.entity.sale.VoucherHistory;
import com.jpoweredcart.common.service.EmailService;
import com.jpoweredcart.common.service.SettingKey;

public class VoucherAdminModelImpl extends BaseModel implements VoucherAdminModel {
	
	@Inject
	private EmailService emailService;
	
	@Transactional
	@Override
	public void create(Voucher voucher) {
		String sql = "INSERT INTO " +quoteTable("voucher")+ "(code, from_name, from_email, " +
				"to_name, to_email, voucher_theme_id, message, amount, status, date_added) VALUES (?,?,?,?,?,?)";
		getJdbcOperations().update(sql, voucher.getCode(), voucher.getFromName(), voucher.getFromEmail(),
				voucher.getToName(), voucher.getToEmail(), voucher.getVoucherThemeId(), voucher.getMessage(),
				voucher.getAmount(), voucher.getStatus(), new Date());
	}

	@Transactional
	@Override
	public void update(Voucher voucher) {
		String sql = "UPDATE " +quoteTable("voucher")+ " SET code=?, from_name=?, from_email=?, " +
				"to_name=?, to_email=?, voucher_theme_id=?, message=?, amount=?, status=?, date_added=? WHERE voucher_id=?";
		getJdbcOperations().update(sql, voucher.getCode(), voucher.getFromName(), voucher.getFromEmail(),
				voucher.getToName(), voucher.getToEmail(), voucher.getVoucherThemeId(), voucher.getMessage(),
				voucher.getAmount(), voucher.getStatus(), voucher.getDateAdded(), voucher.getId());
	}
	
	@Transactional
	@Override
	public void delete(Integer voucherId) {
		String sql = "DELETE FROM "+quoteTable("voucher")+" WHERE voucher_id=?";
		getJdbcOperations().update(sql, voucherId);
		
		sql = "DELETE FROM "+quoteTable("voucher_history")+" WHERE voucher_id=?";
		getJdbcOperations().update(sql, voucherId);
		
	}

	@Override
	public Voucher get(Integer voucherId) {
		String sql = "SELECT * FROM " +quoteTable("voucher")+ " WHERE voucher_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{voucherId}, new VoucherRowMapper());
	}

	@Override
	public Voucher getOneByCode(String code) {
		String sql = "SELECT DISTINCT * FROM " +quoteTable("voucher")+ " WHERE code = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{code}, new VoucherRowMapper());
	}

	@Override
	public List<Voucher> getList(PageParam pageParam) {
		String sql = "SELECT v.voucher_id, v.code, v.from_name, v.from_email, v.to_name, v.to_email, (SELECT vtd.name FROM " 
				+quoteTable("voucher_theme_description")+ " vtd WHERE vtd.voucher_theme_id = v.voucher_theme_id AND vtd.language_id = ?) AS theme, v.amount, v.status, v.date_added FROM " +quoteTable("voucher")+ " v";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, new String[]{
				"v.code", "v.from_name", "v.from_email", "v.to_name", "v.to_email",
				"v.theme", "v.amount", "v.status", "v.date_added"});
		query.addParameters(languageId);
		List< Voucher> vouchers = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new VoucherRowMapper());
		return vouchers;
	}

	@Override
	public void sendVoucher(Integer voucherId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalByThemeId(Integer voucherThemeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<VoucherHistory> getVoucherHistories(Integer voucherId,
			int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalVoucherHistories(Integer voucherId) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
