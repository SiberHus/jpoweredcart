package com.jpoweredcart.admin.model.design.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.form.design.LayoutForm;
import com.jpoweredcart.admin.model.design.LayoutAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.design.Layout;
import com.jpoweredcart.common.entity.design.LayoutRoute;
import com.jpoweredcart.common.entity.design.jdbc.LayoutRouteRowMapper;
import com.jpoweredcart.common.entity.design.jdbc.LayoutRowMapper;

public class LayoutAdminModelImpl extends BaseModel implements LayoutAdminModel {
	
	
	@Transactional
	@Override
	public void create(final LayoutForm layoutForm) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("layout") + "(name) VALUES(?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, layoutForm.getName());
				return ps;
			}
		}, keyHolder);
		
		int layoutId = keyHolder.getKey().intValue();
		
		layoutForm.setId(layoutId);
		addLayoutRoutes(layoutForm);
	}
	
	@Transactional
	@Override
	public void update(LayoutForm layoutForm) {
		
		String sql = "UPDATE " +quoteTable("layout")+ " SET name = ? WHERE layout_id = ?";
		int num = getJdbcOperations().update(sql, layoutForm.getName(), layoutForm.getId());
		if(num>0){
			sql = "DELETE FROM "+quoteTable("layout_route")+ " WHERE layout_id=?";
			getJdbcOperations().update(sql, layoutForm.getId());
			addLayoutRoutes(layoutForm);
		}
	}
	
	private void addLayoutRoutes(LayoutForm layoutForm){
		if(layoutForm.getLayoutRoutes().size()>0){
			String sql = "INSERT INTO " +quoteTable("layout_route")+ " SET layout_id = ?, store_id = ?, route = ?";
			for(LayoutRoute route: layoutForm.getLayoutRoutes()){
				getJdbcOperations().update(sql, layoutForm.getId(), 
						route.getStoreId(), route.getRoute());
			}
		}
	}
	
	@Transactional
	@Override
	public void delete(Integer layoutId) {
		String tables[] = new String[]{"layout", "layout_route", 
				"category_to_layout","product_to_layout","information_to_layout"};
		for(String table: tables){
			String sql = "DELETE FROM "+quoteTable(table)+" WHERE layout_id=?";
			getJdbcOperations().update(sql, layoutId);
		}
	}
	
	@Override
	public LayoutForm newForm(){
		return new LayoutForm();
	}
	
	@Override
	public LayoutForm getForm(Integer layoutId){
		String sql = "SELECT DISTINCT * FROM " +quoteTable("layout")+ " WHERE layout_id =?";
		LayoutForm layoutForm = (LayoutForm)getJdbcOperations().queryForObject(
				sql, new Object[]{layoutId}, 
			new LayoutRowMapper(){
				public LayoutForm newObject(){ return new LayoutForm(); }	
			});
		List<LayoutRoute> layoutRoutes = getLayoutRoutes(layoutId);
		layoutForm.setLayoutRoutes(layoutRoutes);
		return layoutForm;
	}
	
	@Override
	public Layout get(Integer layoutId) {
		
		return getForm(layoutId);
	}
	
	@Override
	public List<Layout> getList(PageParam pageParam) {
		QueryBean query = createPaginationQuery("layout", pageParam, 
				new String[]{"name"});
		List<Layout> layoutList = getJdbcOperations().query(query.getSql(), 
				query.getParameters(), new LayoutRowMapper());
		return layoutList;
	}
	
	@Override
	public List<LayoutRoute> getLayoutRoutes(Integer layoutId) {
		String sql = "SELECT * FROM " +quoteTable("layout_route")+ " WHERE layout_id = ?";
		return getJdbcOperations().query(sql, new Object[]{layoutId}, new LayoutRouteRowMapper());
	}

	@Override
	public int getTotal() {
		String sql = "SELECT COUNT(*) AS total FROM " +quoteTable("layout");
		return getJdbcOperations().queryForObject(sql, Integer.class);
	}
	
	
}
