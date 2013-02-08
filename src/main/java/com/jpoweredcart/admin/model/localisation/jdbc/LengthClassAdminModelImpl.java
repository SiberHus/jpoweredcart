package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.jpoweredcart.admin.model.localisation.LengthClassAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.entity.localisation.LengthClassDesc;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class LengthClassAdminModelImpl extends BaseModel implements LengthClassAdminModel {

	public LengthClassAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final LengthClass lengthClass) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("length_class")+ "(value) VALUES(?) ";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setBigDecimal(1, lengthClass.getValue());
				return ps;
			}
		}, keyHolder);
		Integer lengthClassId = keyHolder.getKey().intValue();
		addDescsToLengthClass(lengthClassId, lengthClass.getDescs());
		lengthClass.setId(lengthClassId);
	}

	@Transactional
	@Override
	public void update(LengthClass lengthClass) {
		
		String sql = "UPDATE " +quoteTable("length_class")+ " SET "+quoteName("value")+"=? WHERE length_class_id=?";
		getJdbcOperations().update(sql, lengthClass.getValue(), lengthClass.getId());
		
		sql = "DELETE FROM " +quoteTable("length_class_description")+ " WHERE length_class_id=?";
		getJdbcOperations().update(sql, lengthClass.getId());
		
		addDescsToLengthClass(lengthClass.getId(), lengthClass.getDescs());
	}
	
	protected void addDescsToLengthClass(Integer lengthClassId, List<LengthClassDesc> descs){
		for(LengthClassDesc desc: descs){
			String sql = "INSERT INTO " +quoteTable("length_class_description")
					+ "(length_class_id, language_id, title, unit) VALUES(?,?,?,?)";
			getJdbcOperations().update(sql, lengthClassId, desc.getLanguageId(),
					desc.getTitle(), desc.getUnit());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer lengthClassId) {
		
		String sql = "DELETE FROM " +quoteTable("length_class")+ " WHERE length_class_id=?";
		getJdbcOperations().update(sql, lengthClassId);
		
		sql = "DELETE FROM " +quoteTable("length_class_description")+ " WHERE length_class_id=?";
		getJdbcOperations().update(sql, lengthClassId);
	}

	
	@Override
	public LengthClass get(Integer lengthClassId) {
		
		String sql = "SELECT * FROM "+quoteTable("length_class")+ " WHERE length_class_id=?";
		LengthClass lengthClass = getJdbcOperations().queryForObject(sql, 
				new Object[]{lengthClassId}, new LengthClassRowMapper());
		
		List<LengthClassDesc> lengthClassDescs = getLengthClassDescs(lengthClassId);
		if(lengthClassDescs!=null){
			lengthClass.setDescs(lengthClassDescs);
		}
		return lengthClass;
	}
	
	
	@Override
	public List<LengthClass> getList(PageParam pageParam) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("length_class")+ " wc LEFT JOIN " +quoteTable("length_class_description")+ " wcd ON (wc.length_class_id = wcd.length_class_id) WHERE wcd.language_id = ?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, 
				new String[]{"title", "unit", "value"});
		query.addParameters(languageId);
		List<LengthClass> lengthClassList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new LengthClassRowMapper());
		return lengthClassList;
	}
	
	@Override
	public List<LengthClassDesc> getLengthClassDescsByUnit(String unit) {
		
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT * FROM " +quoteTable("length_class_description")+ " WHERE unit = ? AND language_id = ?";
		List<LengthClassDesc> lengthClassDescList = getJdbcOperations().query(sql, 
				new Object[]{languageId}, new LengthClassRowMapper.Desc());
		return lengthClassDescList;
	}
	
	@Override
	public List<LengthClassDesc> getLengthClassDescs(Integer lengthClassId) {
		
		String sql = "SELECT wc.length_class_id, wc.language_id, l.name AS language_name, l.image AS language_image, wc.title, wc.unit FROM " +
				quoteTable("length_class_description")+" wc INNER JOIN "+quoteTable("language")+" l ON wc.language_id=l.language_id WHERE length_class_id=?";
		
		return getJdbcOperations().query(sql, new Object[]{lengthClassId}, 
				new LengthClassRowMapper.Desc());
	}
	
	@Override
	public int getTotal() {
		
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("length_class");
		return getJdbcOperations().queryForInt(sql);
	}
	
}