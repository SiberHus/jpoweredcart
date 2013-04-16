package com.jpoweredcart.admin.model.sale.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.sale.VoucherForm;
import com.jpoweredcart.admin.model.sale.OrderAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherThemeAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.sale.Order;
import com.jpoweredcart.common.entity.sale.Voucher;
import com.jpoweredcart.common.entity.sale.VoucherHistory;
import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.jdbc.VoucherHistoryRowMapper;
import com.jpoweredcart.common.entity.sale.jdbc.VoucherRowMapper;
import com.jpoweredcart.common.service.CurrencyService;
import com.jpoweredcart.common.system.email.EmailMessage;
import com.jpoweredcart.common.system.email.EmailService;
import com.jpoweredcart.common.system.media.MediaService;
import com.jpoweredcart.common.system.setting.SettingKey;
import com.jpoweredcart.common.system.template.TemplateService;

public class VoucherAdminModelImpl extends BaseModel implements VoucherAdminModel {
	
	@Inject
	private EmailService emailService;
	
	@Inject
	private TemplateService templateService;
	
	@Inject
	private VoucherThemeAdminModel voucherThemeAdminModel;
	
	@Inject
	private OrderAdminModel orderAdminModel;
	
	@Inject
	private CurrencyService currencyService;
	
	@Inject
	private MediaService mediaService;
	
	@Transactional
	@Override
	public void create(VoucherForm voucherForm) {
		String sql = "INSERT INTO " +quoteTable("voucher")+ "(code, from_name, from_email, " +
				"to_name, to_email, voucher_theme_id, message, amount, status, date_added) VALUES (?,?,?,?,?,?)";
		getJdbcOperations().update(sql, voucherForm.getCode(), voucherForm.getFromName(), voucherForm.getFromEmail(),
				voucherForm.getToName(), voucherForm.getToEmail(), voucherForm.getVoucherThemeId(), voucherForm.getMessage(),
				voucherForm.getAmount(), voucherForm.getStatus(), new Date());
	}

	@Transactional
	@Override
	public void update(VoucherForm voucherForm) {
		String sql = "UPDATE " +quoteTable("voucher")+ " SET code=?, from_name=?, from_email=?, " +
				"to_name=?, to_email=?, voucher_theme_id=?, message=?, amount=?, status=?, date_added=? WHERE voucher_id=?";
		getJdbcOperations().update(sql, voucherForm.getCode(), voucherForm.getFromName(), voucherForm.getFromEmail(),
				voucherForm.getToName(), voucherForm.getToEmail(), voucherForm.getVoucherThemeId(), voucherForm.getMessage(),
				voucherForm.getAmount(), voucherForm.getStatus(), voucherForm.getDateAdded(), voucherForm.getId());
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
	public VoucherForm newForm() {
		return new VoucherForm();
	}

	@Override
	public VoucherForm getForm(Integer voucherId) {
		return (VoucherForm)get(voucherId, VoucherForm.class);
	}

	@Override
	public Voucher get(Integer voucherId, Class<? extends Voucher> clazz) {
		String sql = "SELECT * FROM " +quoteTable("voucher")+ " WHERE voucher_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{voucherId}, 
				new VoucherRowMapper().setTargetClass(clazz));
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
		//sortedKeys={"v.code", "v.from_name", "v.from_email", "v.to_name", "v.to_email","v.theme", "v.amount", "v.status", "v.date_added"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		query.addParameters(languageId);
		List< Voucher> vouchers = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new VoucherRowMapper());
		return vouchers;
	}

	@Override
	public void sendVoucher(Integer voucherId) {
		Voucher voucher = get(voucherId, Voucher.class);
		if(voucher==null){
			return;
		}
		
		VoucherTheme voucherTheme = voucherThemeAdminModel
				.get(voucher.getVoucherThemeId(), VoucherTheme.class);
		
		Map<String, Object> model = new HashMap<String, Object>(); 
		model.put("fromName", voucher.getFromName());
		
		String storeName = null;
		String storeUrl = null;
		
		String image = voucherTheme.getImage();
		image = mediaService.getImageUrl(image);
		model.put("image", image);
		
		Integer orderId = voucher.getOrderId();
		Order order = orderAdminModel.get(orderId, Order.class);
		// If voucher belongs to an order
		if(order!=null){
			Integer languageId = order.getLanguageId();
			String amount = currencyService.format(voucher.getAmount(), order.getCurrencyCode(),
					order.getCurrencyValue(), languageId);
			model.put("amount", amount);
			storeName = order.getStoreName();
			storeUrl = order.getStoreUrl();
		}else{
			String languageCode = getSettingService().getConfig(SettingKey.CFG_LANGUAGE);
			
//			String amount = currencyService.format(voucher.getAmount(), order.getCurrencyCode(),
//					order.getCurrencyValue(), );
//			model.put("amount", amount);
		}
		
		model.put("code", voucher.getCode());
		model.put("storeName", storeUrl);
		model.put("storeUrl", storeUrl);
		
		String text = templateService.renderTemplate("/admin/email/voucher", model);
		EmailMessage email = new EmailMessage();
		email.setTo(voucher.getToEmail());
		email.setSenderName(storeName);
		email.setBodyHtml(text);
		
		emailService.send(email);
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("voucher");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}

	@Override
	public int getTotalByThemeId(Integer voucherThemeId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("voucher")+" WHERE voucher_theme_id =?";
		return getJdbcOperations().queryForObject(sql, new Object[]{voucherThemeId}, Integer.class);
	}

	@Override
	public List<VoucherHistory> getHistories(Integer voucherId,
			int start, int limit) {
		String sql = "SELECT vh.order_id, o.firstname, o.lastname, vh.amount, vh.date_added FROM "+
			quoteTable("voucher_history")+" vh LEFT JOIN "+quoteTable("order")+
			" o ON (vh.order_id = o.order_id) WHERE vh.voucher_id =?";
		PageParam pageParam = PageParam.list(start, limit);
		pageParam.addOrder("vh.date_added", "ASC");
		QueryBean query = createPaginationQuery(sql, pageParam);
		return getJdbcOperations().query(query.getSql(), query.getParameters(), 
			new VoucherHistoryRowMapper(){
			@Override
			public VoucherHistory mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				VoucherHistory vh = super.mapRow(rs, rowNum);
				vh.setCustomer(rs.getString("firstname")+" "+rs.getString("lastname"));
				return vh;
			}
		});
	}

	@Override
	public int getTotalHistories(Integer voucherId) {
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("voucher_history")+" WHERE voucher_id=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{voucherId}, Integer.class);
	}
	
}
