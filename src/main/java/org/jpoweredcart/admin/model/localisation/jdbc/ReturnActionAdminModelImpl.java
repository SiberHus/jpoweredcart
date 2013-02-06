package org.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.jpoweredcart.admin.model.localisation.ReturnActionAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.localisation.ReturnActions;
import org.jpoweredcart.common.entity.localisation.ReturnActions.ReturnAction;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class ReturnActionAdminModelImpl extends BaseModel implements ReturnActionAdminModel {
	
	public ReturnActionAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void addReturnAction(ReturnAction... returnActions) {
		Integer returnActionId = null;
		for(final ReturnAction returnAction: returnActions){
			if(returnActionId!=null){
				String sql = "INSERT INTO "+quoteTable("return_action")+"(return_action_id, language_id, name) VALUES(?, ?, ?)";
				getJdbcOperations().update(sql, returnActionId, 
						returnAction.getLanguageId(), returnAction.getName());
			}else{
				KeyHolder keyHolder = new GeneratedKeyHolder();
				getJdbcOperations().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						String sql = "INSERT INTO "+quoteTable("return_action")+"(language_id, name) VALUES(?, ?)";
						PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, returnAction.getLanguageId());
						ps.setString(2, returnAction.getName());
						return ps;
					}
				}, keyHolder);
				returnActionId = keyHolder.getKey().intValue();
			}
		}
	}
	
	@Transactional
	@Override
	public void updateReturnAction(ReturnAction... returnActions) {
		String sql = "UPDATE " +quoteTable("return_action")+ " SET name = ? " +
				" WHERE return_action_id = ? and language_id = ?";
		for(ReturnAction desc: returnActions){
			getJdbcOperations().update(sql, desc.getName(), 
					desc.getId(), desc.getLanguageId());
		}
	}
	
	@Transactional
	@Override
	public void deleteReturnAction(Integer returnActionId) {
		String sql = "DELETE FROM " +quoteTable("return_action")+ " WHERE return_action_id = ?";
		getJdbcOperations().update(sql, returnActionId);
	}
	
	@Override
	public ReturnAction getReturnAction(Integer returnActionId) {
		String sql = "SELECT * FROM " +quoteTable("return_action")+ " WHERE return_action_id = ? and language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, new Object[]{returnActionId, languageId}, 
				new ReturnActionRowMapper());
	}
	
	@Override
	public List<ReturnAction> getReturnActions(PageParam pageParam) {
		String sql = "SELECT * FROM "+quoteTable("return_action")+" WHERE language_id=?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, new String[]{"name"});
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		query.addParameter(languageId);
		List<ReturnAction> returnActionList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new ReturnActionRowMapper());
		
		return returnActionList;
	}
	
	@Override
	public ReturnActions getReturnActions(Integer descId) {
		String sql = "SELECT s.return_action_id, s.name, s.language_id, l.name AS language_name, l.image AS language_image FROM " +
				quoteTable("return_action")+" s INNER JOIN "+quoteTable("language")+" l ON s.language_id=l.language_id WHERE return_action_id=?";
		List<ReturnAction> list = getJdbcOperations().query(sql, new Object[]{descId}, 
				new ReturnActionRowMapper());
		ReturnActions returnActions = new ReturnActions(list);
		return returnActions;
	}
	
	@Override
	public int getTotalReturnActions() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("return_action")+ " WHERE language_id=?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForInt(sql, languageId);
	}
	
	
}
