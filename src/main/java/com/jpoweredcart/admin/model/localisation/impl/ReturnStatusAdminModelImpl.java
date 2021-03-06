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

import com.jpoweredcart.admin.model.localisation.ReturnStatusAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.ReturnStatuses;
import com.jpoweredcart.common.entity.localisation.ReturnStatuses.ReturnStatus;
import com.jpoweredcart.common.entity.localisation.jdbc.ReturnStatusRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class ReturnStatusAdminModelImpl extends BaseModel implements ReturnStatusAdminModel {
	
	
	@Transactional
	@Override
	public void create(ReturnStatus... returnStatuses) {
		Integer returnStatusId = null;
		for(final ReturnStatus returnStatus: returnStatuses){
			if(returnStatusId!=null){
				String sql = "INSERT INTO "+quoteTable("return_status")+"(return_status_id, language_id, name) VALUES(?, ?, ?)";
				getJdbcOperations().update(sql, returnStatusId, 
						returnStatus.getLanguageId(), returnStatus.getName());
			}else{
				KeyHolder keyHolder = new GeneratedKeyHolder();
				getJdbcOperations().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						String sql = "INSERT INTO "+quoteTable("return_status")+"(language_id, name) VALUES(?, ?)";
						PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, returnStatus.getLanguageId());
						ps.setString(2, returnStatus.getName());
						return ps;
					}
				}, keyHolder);
				returnStatusId = keyHolder.getKey().intValue();
			}
		}
	}
	
	@Transactional
	@Override
	public void update(ReturnStatus... returnStatuses) {
		String sql = "UPDATE " +quoteTable("return_status")+ " SET name = ? " +
				" WHERE return_status_id = ? and language_id = ?";
		for(ReturnStatus desc: returnStatuses){
			getJdbcOperations().update(sql, desc.getName(), 
					desc.getId(), desc.getLanguageId());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer returnStatusId) {
		String sql = "DELETE FROM " +quoteTable("return_status")+ " WHERE return_status_id = ?";
		getJdbcOperations().update(sql, returnStatusId);
	}
	
	@Override
	public ReturnStatus get(Integer returnStatusId) {
		String sql = "SELECT * FROM " +quoteTable("return_status")+ " WHERE return_status_id = ? and language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, new Object[]{returnStatusId, languageId}, 
				new ReturnStatusRowMapper());
	}
	
	@Override
	public List<ReturnStatus> getList(PageParam pageParam) {
		String sql = "SELECT * FROM "+quoteTable("return_status")+" WHERE language_id=?";
		//sortedKeys={"name"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		query.addParameters(languageId);
		List<ReturnStatus> returnStatusList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new ReturnStatusRowMapper());
		
		return returnStatusList;
	}
	
	@Override
	public ReturnStatuses getReturnStatuses(Integer descId) {
		String sql = "SELECT s.return_status_id, s.name, s.language_id, l.name AS language_name, l.image AS language_image FROM " +
				quoteTable("return_status")+" s INNER JOIN "+quoteTable("language")+" l ON s.language_id=l.language_id WHERE return_status_id=?";
		List<ReturnStatus> list = getJdbcOperations().query(sql, new Object[]{descId}, 
				new ReturnStatusRowMapper());
		ReturnStatuses returnStatuses = new ReturnStatuses(list);
		return returnStatuses;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("return_status")+ " WHERE language_id=?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, Integer.class, languageId);
	}
	
	
}
