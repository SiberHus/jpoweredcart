package com.jpoweredcart.admin.model.sale.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.sale.VoucherThemeForm;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherThemeAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.entity.sale.VoucherThemeDesc;
import com.jpoweredcart.common.entity.sale.jdbc.VoucherThemeRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class VoucherThemeAdminModelImpl extends BaseModel implements VoucherThemeAdminModel {

	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Transactional
	@Override
	public void create(final VoucherThemeForm vtForm) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO "+quoteTable("voucher_theme")+" SET image = ?";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, vtForm.getImage());
				return ps;
			}
		}, keyHolder);
		Integer attrId = keyHolder.getKey().intValue();
		vtForm.setId(attrId);
		setAdditionalFormValues(vtForm);
	}

	@Transactional
	@Override
	public void update(VoucherThemeForm vtForm) {
		String sql = "UPDATE " +quoteTable("voucher_theme")+ " SET image=? WHERE voucher_theme_id=?";
		getJdbcOperations().update(sql, vtForm.getImage(), vtForm.getId());
		
		sql = "DELETE FROM " +quoteTable("voucher_theme_description")+ " WHERE voucher_theme_id=?";
		getJdbcOperations().update(sql, vtForm.getId());
		
		setAdditionalFormValues(vtForm);
	}
	
	protected void setAdditionalFormValues(VoucherThemeForm vtForm){
		for(VoucherThemeDesc desc: vtForm.getDescs()){
			String sql = "INSERT INTO "+quoteTable("voucher_theme_description")+
				" SET voucher_theme_id = ?, language_id = ?, name = ?";
			getJdbcOperations().update(sql, vtForm.getId(), 
					desc.getLanguageId(), desc.getName());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer vtId) {
		String tables[] = new String[]{"voucher_theme", "voucher_theme_description"};
		for(String table: tables){
			String sql = "DELETE FROM " +quoteTable(table)+ " WHERE voucher_theme_id=?";
			getJdbcOperations().update(sql, vtId);
		}
	}
	
	@Override
	public VoucherThemeForm newForm(){
		VoucherThemeForm vtForm = new VoucherThemeForm();
		vtForm.setDescs(languageAdminModel
				.createDescriptionList(VoucherThemeDesc.class));
		return vtForm;
	}
	
	@Override
	public VoucherThemeForm getForm(Integer vtId){
		
		VoucherThemeForm vtForm = (VoucherThemeForm)get(vtId, VoucherThemeForm.class);
		vtForm.setDescs(getDescriptions(vtId));
		return vtForm;
	}
	
	@Override
	public VoucherTheme get(Integer vtId, Class<? extends VoucherTheme> clazz) {
		String sql = "SELECT * FROM " +quoteTable("voucher_theme")+ " WHERE voucher_theme_id = ?";
		return getJdbcOperations().queryForObject(sql, new Object[]{vtId}, 
				new VoucherThemeRowMapper().setTargetClass(clazz));
	}
	
	@Override
	public List<VoucherTheme> getList(PageParam pageParam) {
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("voucher_theme")+ " vt LEFT JOIN " +quoteTable("voucher_theme_description")
				+ " vtd ON (vt.voucher_theme_id = vtd.voucher_theme_id) WHERE vtd.language_id = ?";
		//sortedKeys={"vtd.name"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		query.addParameters(languageId);
		List<VoucherTheme> customerGroupList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new VoucherThemeRowMapper(){
					@Override
					public VoucherTheme mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						VoucherTheme vt = super.mapRow(rs, rowNum);
						vt.setName(rs.getString("name"));
						return vt;
					}
				});
		return customerGroupList;
	}
	
	public List<VoucherThemeDesc> getDescriptions(Integer vtId){
		String sql = "SELECT vtd.voucher_theme_id, vtd.language_id, l.name AS language_name, l.image AS language_image, vtd.name FROM " +
				quoteTable("voucher_theme_description")+" vtd INNER JOIN "
				+quoteTable("language")+" l ON vtd.language_id=l.language_id WHERE vtd.voucher_theme_id=?";
		
		return getJdbcOperations().query(sql, 
				new Object[]{vtId}, new VoucherThemeRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " + quoteTable("voucher_theme");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}

}
