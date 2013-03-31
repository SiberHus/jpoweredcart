package com.jpoweredcart.admin.model.localisation.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.model.localisation.StockStatusAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.StockStatuses;
import com.jpoweredcart.common.entity.localisation.StockStatuses.StockStatus;
import com.jpoweredcart.common.service.setting.SettingKey;

public class StockStatusAdminModelImpl extends BaseModel implements StockStatusAdminModel {
	
	
	@Transactional
	@Override
	public void create(StockStatus... stockStatuses) {
		Integer stockStatusId = null;
		for(final StockStatus stockStatus: stockStatuses){
			if(stockStatusId!=null){
				String sql = "INSERT INTO "+quoteTable("stock_status")+"(stock_status_id, language_id, name) VALUES(?, ?, ?)";
				getJdbcOperations().update(sql, stockStatusId, 
						stockStatus.getLanguageId(), stockStatus.getName());
			}else{
				KeyHolder keyHolder = new GeneratedKeyHolder();
				getJdbcOperations().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						String sql = "INSERT INTO "+quoteTable("stock_status")+"(language_id, name) VALUES(?, ?)";
						PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, stockStatus.getLanguageId());
						ps.setString(2, stockStatus.getName());
						return ps;
					}
				}, keyHolder);
				stockStatusId = keyHolder.getKey().intValue();
			}
		}
	}
	
	@Transactional
	@Override
	public void update(StockStatus... stockStatuses) {
		String sql = "UPDATE " +quoteTable("stock_status")+ " SET name = ? " +
				" WHERE stock_status_id = ? and language_id = ?";
		for(StockStatus desc: stockStatuses){
			getJdbcOperations().update(sql, desc.getName(), 
					desc.getId(), desc.getLanguageId());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer stockStatusId) {
		String sql = "DELETE FROM " +quoteTable("stock_status")+ " WHERE stock_status_id = ?";
		getJdbcOperations().update(sql, stockStatusId);
	}
	
	@Override
	public StockStatus get(Integer stockStatusId) {
		String sql = "SELECT * FROM " +quoteTable("stock_status")+ " WHERE stock_status_id = ? and language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, new Object[]{stockStatusId, languageId}, 
				new StockStatusRowMapper());
	}
	
	@Override
	public List<StockStatus> getStockStatuses(PageParam pageParam) {
		String sql = "SELECT * FROM "+quoteTable("stock_status")+" WHERE language_id=?";
		QueryBean query = createPaginationQueryFromSql(sql, pageParam, new String[]{"name"});
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		query.addParameters(languageId);
		List<StockStatus> stockStatusList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new StockStatusRowMapper());
		
		return stockStatusList;
	}
	
	@Override
	public StockStatuses getStockStatuses(Integer descId) {
		String sql = "SELECT s.stock_status_id, s.name, s.language_id, l.name AS language_name, l.image AS language_image FROM " +
				quoteTable("stock_status")+" s INNER JOIN "+quoteTable("language")+" l ON s.language_id=l.language_id WHERE stock_status_id=?";
		List<StockStatus> list = getJdbcOperations().query(sql, new Object[]{descId}, 
				new StockStatusRowMapper());
		StockStatuses stockStatuses = new StockStatuses(list);
		return stockStatuses;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("stock_status")+ " WHERE language_id=?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForInt(sql, languageId);
	}
	
	
}
