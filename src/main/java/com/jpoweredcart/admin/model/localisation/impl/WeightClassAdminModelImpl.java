package com.jpoweredcart.admin.model.localisation.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.localisation.WeightClassAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.WeightClass;
import com.jpoweredcart.common.entity.localisation.WeightClassDesc;
import com.jpoweredcart.common.entity.localisation.jdbc.WeightClassRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class WeightClassAdminModelImpl extends BaseModel implements WeightClassAdminModel {
	
	
	@Transactional
	@Override
	public void create(final WeightClass weightClass) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("weight_class")+ "(value) VALUES(?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setBigDecimal(1, weightClass.getValue());
				return ps;
			}
		}, keyHolder);
		Integer weightClassId = keyHolder.getKey().intValue();
		addDescsToWeightClass(weightClassId, weightClass.getDescs());
	}

	@Transactional
	@Override
	public void update(WeightClass weightClass) {
		
		String sql = "UPDATE " +quoteTable("weight_class")+ " SET "+quoteName("value")+"=? WHERE weight_class_id=?";
		getJdbcOperations().update(sql, weightClass.getValue(), weightClass.getId());
		
		sql = "DELETE FROM " +quoteTable("weight_class_description")+ " WHERE weight_class_id=?";
		getJdbcOperations().update(sql, weightClass.getId());
		
		addDescsToWeightClass(weightClass.getId(), weightClass.getDescs());
	}
	
	private void addDescsToWeightClass(Integer weightClassId, List<WeightClassDesc> descs){
		for(WeightClassDesc desc: descs){
			String sql = "INSERT INTO " +quoteTable("weight_class_description")
					+ "(weight_class_id, language_id, title, unit) VALUES(?,?,?,?)";
			getJdbcOperations().update(sql, weightClassId, desc.getLanguageId(),
					desc.getTitle(), desc.getUnit());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer weightClassId) {
		
		String sql = "DELETE FROM " +quoteTable("weight_class")+ " WHERE weight_class_id=?";
		getJdbcOperations().update(sql, weightClassId);
		
		sql = "DELETE FROM " +quoteTable("weight_class_description")+ " WHERE weight_class_id=?";
		getJdbcOperations().update(sql, weightClassId);
	}

	
	@Override
	public WeightClass get(Integer weightClassId) {
		
		String sql = "SELECT * FROM "+quoteTable("weight_class")+ " WHERE weight_class_id=?";
		WeightClass weightClass = getJdbcOperations().queryForObject(sql, 
				new Object[]{weightClassId}, new WeightClassRowMapper());
		
		List<WeightClassDesc> weightClassDescs = getWeightClassDescs(weightClassId);
		if(weightClassDescs!=null){
			weightClass.setDescs(weightClassDescs);
		}
		return weightClass;
	}

	
	@Override
	public List<WeightClass> getList(PageParam pageParam) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("weight_class")+ " wc LEFT JOIN " +quoteTable("weight_class_description")+ " wcd ON (wc.weight_class_id = wcd.weight_class_id) WHERE wcd.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"title", "unit", "value"});
		query.addParameters(languageId);
		List<WeightClass> weightClassList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new WeightClassRowMapper());
		return weightClassList;
	}
	
	@Override
	public List<WeightClassDesc> getWeightClassDescsByUnit(String unit) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("weight_class_description")+ " WHERE unit = ? AND language_id = ?";
		List<WeightClassDesc> weightClassDescList = getJdbcOperations().query(sql, 
				new Object[]{languageId}, new WeightClassRowMapper.Desc());
		return weightClassDescList;
	}
	
	@Override
	public List<WeightClassDesc> getWeightClassDescs(Integer weightClassId) {
		
		String sql = "SELECT wc.weight_class_id, wc.language_id, l.name AS language_name, l.image AS language_image, wc.title, wc.unit FROM " +
				quoteTable("weight_class_description")+" wc INNER JOIN "+quoteTable("language")+" l ON wc.language_id=l.language_id WHERE weight_class_id=?";
		
		return getJdbcOperations().query(sql, new Object[]{weightClassId}, 
				new WeightClassRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("weight_class");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
}
