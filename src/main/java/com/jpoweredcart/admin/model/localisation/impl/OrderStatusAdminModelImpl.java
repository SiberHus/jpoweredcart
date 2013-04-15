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

import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.localisation.OrderStatuses;
import com.jpoweredcart.common.entity.localisation.OrderStatuses.OrderStatus;
import com.jpoweredcart.common.entity.localisation.jdbc.OrderStatusRowMapper;
import com.jpoweredcart.common.system.setting.SettingKey;

public class OrderStatusAdminModelImpl extends BaseModel implements OrderStatusAdminModel {
	
	
	@Transactional
	@Override
	public void create(OrderStatus... orderStatuses) {
		Integer orderStatusId = null;
		for(final OrderStatus desc: orderStatuses){
			if(orderStatusId!=null){
				String sql = "INSERT INTO "+quoteTable("order_status")+"(order_status_id, language_id, name) VALUES(?, ?, ?)";
				getJdbcOperations().update(sql, orderStatusId, 
						desc.getLanguageId(), desc.getName());
			}else{
				KeyHolder keyHolder = new GeneratedKeyHolder();
				getJdbcOperations().update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						String sql = "INSERT INTO "+quoteTable("order_status")+"(language_id, name) VALUES(?, ?)";
						PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						ps.setInt(1, desc.getLanguageId());
						ps.setString(2, desc.getName());
						return ps;
					}
				}, keyHolder);
				orderStatusId = keyHolder.getKey().intValue();
			}
		}
	}
	
	@Transactional
	@Override
	public void update(OrderStatus... orderStatuses) {
		String sql = "UPDATE " +quoteTable("order_status")+ " SET name = ? " +
				" WHERE order_status_id = ? and language_id = ?";
		for(OrderStatus orderStatus: orderStatuses){
			getJdbcOperations().update(sql, orderStatus.getName(), 
					orderStatus.getId(), orderStatus.getLanguageId());
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer orderStatusId) {
		String sql = "DELETE FROM " +quoteTable("order_status")+ " WHERE order_status_id = ?";
		getJdbcOperations().update(sql, orderStatusId);
	}
	
	@Override
	public OrderStatus get(Integer orderStatusId) {
		String sql = "SELECT * FROM " +quoteTable("order_status")+ " WHERE order_status_id = ? and language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, new Object[]{orderStatusId, languageId}, 
				new OrderStatusRowMapper());
	}
	
	@Override
	public List<OrderStatus> getList(PageParam pageParam) {
		String sql = "SELECT * FROM "+quoteTable("order_status")+" WHERE language_id=?";
		//sortedKeys={"name"}
		QueryBean query = createPaginationQuery(sql, pageParam);
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		query.addParameters(languageId);
		List<OrderStatus> orderStatusList = getJdbcOperations()
				.query(query.getSql(), query.getParameters(), new OrderStatusRowMapper());
		
		return orderStatusList;
	}
	
	@Override
	public OrderStatuses getOrderStatuses(Integer orderStatusId) {
		String sql = "SELECT s.order_status_id, s.name, s.language_id, l.name AS language_name, l.image AS language_image FROM " +
				quoteTable("order_status")+" s INNER JOIN "+quoteTable("language")+" l ON s.language_id=l.language_id WHERE order_status_id=?";
		List<OrderStatus> list = getJdbcOperations().query(sql, new Object[]{orderStatusId}, 
				new OrderStatusRowMapper());
		OrderStatuses orderStatuses = new OrderStatuses(list);
		return orderStatuses;
	}
	
	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("order_status")+ " WHERE language_id=?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		return getJdbcOperations().queryForObject(sql, Integer.class, languageId);
	}
	
	
}
