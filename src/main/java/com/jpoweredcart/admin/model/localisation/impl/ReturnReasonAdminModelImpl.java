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

import com.jpoweredcart.admin.model.localisation.ReturnReasonAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.ReturnReasons;
import com.jpoweredcart.common.entity.localisation.ReturnReasons.ReturnReason;
import com.jpoweredcart.common.entity.localisation.jdbc.ReturnReasonRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class ReturnReasonAdminModelImpl extends BaseModel implements ReturnReasonAdminModel {
	
	
	@Transactional
	@Override
	public void create(ReturnReason... returnReasons) {
		Integer returnReasonId = null;
		for(final ReturnReason returnReason: returnReasons){
			if(returnReasonId!=null){
				String sql = "INSERT INTO "+quoteTable("return_reason")+"(return_reason_id, language_id, name) VALUES(?, ?, ?)";
				getJdbcOperations().update(sql, returnReasonId, 
						returnReason.getLanguageId(), returnReason.getName());
			}else{
				KeyHolder keyHolder = new GeneratedKeyHolder();
				getJdbcOperations().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						String sql = "INSERT INTO "+quoteTable("return_reason")+"(language_id, name) VALUES(?, ?)";
						PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, returnReason.getLanguageId());
						ps.setString(2, returnReason.getName());
						return ps;
					}
				}, keyHolder);
				returnReasonId = keyHolder.getKey().intValue();
			}
		}
	}
	
	@Transactional
	@Override
	public void update(ReturnReason... returnReasons) {
		String sql = "UPDATE " +quoteTable("return_reason")+ " SET name = ? " +
				" WHERE return_reason_id = ? and language_id = ?";
		for(ReturnReason desc: returnReasons){
			getJdbcOperations().update(sql, desc.getName(), 
					desc.getId(), desc.getLanguageId());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer returnReasonId) {
		String sql = "DELETE FROM " +quoteTable("return_reason")+ " WHERE return_reason_id = ?";
		getJdbcOperations().update(sql, returnReasonId);
	}
	
	@Override
	public ReturnReason get(Integer returnReasonId) {
		String sql = "SELECT * FROM " +quoteTable("return_reason")+ " WHERE return_reason_id = ? and language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, new Object[]{returnReasonId, languageId}, 
				new ReturnReasonRowMapper());
	}
	
	@Override
	public List<ReturnReason> getList(PageParam pageParam) {
		String sql = "SELECT * FROM "+quoteTable("return_reason")+" WHERE language_id=?";
		//sortedKeys={"name"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		query.addParameters(languageId);
		List<ReturnReason> returnReasonList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new ReturnReasonRowMapper());
		
		return returnReasonList;
	}
	
	@Override
	public ReturnReasons getReturnReasons(Integer descId) {
		String sql = "SELECT s.return_reason_id, s.name, s.language_id, l.name AS language_name, l.image AS language_image FROM " +
				quoteTable("return_reason")+" s INNER JOIN "+quoteTable("language")+" l ON s.language_id=l.language_id WHERE return_reason_id=?";
		List<ReturnReason> list = getJdbcOperations().query(sql, new Object[]{descId}, 
				new ReturnReasonRowMapper());
		ReturnReasons returnReasons = new ReturnReasons(list);
		return returnReasons;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("return_reason")+ " WHERE language_id=?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, Integer.class, languageId);
	}
	
	
}
