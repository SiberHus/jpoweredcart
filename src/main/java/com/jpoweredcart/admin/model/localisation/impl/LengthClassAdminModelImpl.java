package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.localisation.LengthClassForm;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.localisation.LengthClassAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.entity.localisation.LengthClassDesc;
import com.jpoweredcart.common.entity.localisation.jdbc.LengthClassRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class LengthClassAdminModelImpl extends BaseModel implements LengthClassAdminModel {

	@Inject
	private LanguageAdminModel languageAdminModel;
	
	
	@Transactional
	@Override
	public void create(final LengthClassForm lc) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("length_class")+ "(value) VALUES(?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setBigDecimal(1, lc.getValue());
				return ps;
			}
		}, keyHolder);
		Integer lcId = keyHolder.getKey().intValue();
		addDescsToLengthClass(lcId, lc.getDescs());
		lc.setId(lcId);
	}

	@Transactional
	@Override
	public void update(LengthClassForm lc) {
		
		String sql = "UPDATE " +quoteTable("length_class")+ " SET "+quoteName("value")+"=? WHERE length_class_id=?";
		getJdbcOperations().update(sql, lc.getValue(), lc.getId());
		
		sql = "DELETE FROM " +quoteTable("length_class_description")+ " WHERE length_class_id=?";
		getJdbcOperations().update(sql, lc.getId());
		
		addDescsToLengthClass(lc.getId(), lc.getDescs());
	}
	
	protected void addDescsToLengthClass(Integer lcId, List<LengthClassDesc> descs){
		for(LengthClassDesc desc: descs){
			String sql = "INSERT INTO " +quoteTable("length_class_description")
					+ "(length_class_id, language_id, title, unit) VALUES(?,?,?,?)";
			getJdbcOperations().update(sql, lcId, desc.getLanguageId(),
					desc.getTitle(), desc.getUnit());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer lcId) {
		
		String sql = "DELETE FROM " +quoteTable("length_class")+ " WHERE length_class_id=?";
		getJdbcOperations().update(sql, lcId);
		
		sql = "DELETE FROM " +quoteTable("length_class_description")+ " WHERE length_class_id=?";
		getJdbcOperations().update(sql, lcId);
	}

	@Override
	public LengthClassForm newForm(){
		LengthClassForm lcForm = new LengthClassForm();
		List<Language> languages = languageAdminModel.getList(new PageParam());
		List<LengthClassDesc> descs = new ArrayList<LengthClassDesc>();
		for(Language language: languages){
			LengthClassDesc desc = new LengthClassDesc();
			desc.setLanguageId(language.getId());
			desc.setLanguageName(language.getName());
			desc.setLanguageImage(language.getImage());
			descs.add(desc);
		}
		lcForm.setDescs(descs);
		return lcForm;
	}
	
	@Override
	public LengthClassForm getForm(Integer lcId) {
		
		LengthClassForm lcForm = (LengthClassForm)get(lcId, LengthClassForm.class);
		
		List<LengthClassDesc> desc = getDescriptions(lcId);
		if(desc!=null){
			lcForm.setDescs(desc);
		}
		
		return lcForm;
	}
	
	@Override
	public LengthClass get(Integer lcId, Class<? extends LengthClass> clazz) {
		
		String sql = "SELECT * FROM "+quoteTable("length_class")+ " WHERE length_class_id=?";
		return getJdbcOperations().queryForObject(sql, new Object[]{lcId}, 
				new LengthClassRowMapper().setTargetClass(clazz));
	}
	
	
	@Override
	public List<LengthClass> getList(PageParam pageParam) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("length_class")+ " wc LEFT JOIN " +quoteTable("length_class_description")+ 
				" wcd ON (wc.length_class_id = wcd.length_class_id) WHERE wcd.language_id = ?";
		//sortedKeys={"title", "unit", "value"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		query.addParameters(languageId);
		List<LengthClass> lengthClassList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new LengthClassRowMapper(){
					@Override
					public LengthClass mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						LengthClass lc = super.mapRow(rs, rowNum);
						lc.setTitle(rs.getString("title"));
						lc.setUnit(rs.getString("unit"));
						return lc;
					}
		});
		return lengthClassList;
	}
	
	@Override
	public List<LengthClassDesc> getDescriptionsByUnit(String unit) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("length_class_description")+ " WHERE unit = ? AND language_id = ?";
		List<LengthClassDesc> descs = getJdbcOperations().query(sql, 
				new Object[]{languageId}, new LengthClassRowMapper.Desc());
		return descs;
	}
	
	@Override
	public List<LengthClassDesc> getDescriptions(Integer lengthClassId) {
		
		String sql = "SELECT wc.length_class_id, wc.language_id, l.name AS language_name, l.image AS language_image, wc.title, wc.unit FROM " +
				quoteTable("length_class_description")+" wc INNER JOIN "+quoteTable("language")+" l ON wc.language_id=l.language_id WHERE length_class_id=?";
		
		return getJdbcOperations().query(sql, new Object[]{lengthClassId}, 
				new LengthClassRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("length_class");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
}
