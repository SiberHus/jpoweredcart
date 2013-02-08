package org.jpoweredcart.admin.model.design.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.jpoweredcart.admin.model.design.LayoutAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.QueryBean;
import org.jpoweredcart.common.entity.design.Layout;
import org.jpoweredcart.common.entity.design.LayoutRoute;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

public class LayoutAdminModelImpl extends BaseModel implements LayoutAdminModel {

	public LayoutAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Transactional
	@Override
	public void create(final Layout layout) {
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcOperations().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				String sql = "INSERT INTO " +quoteTable("layout") + "(name) VALUES(?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, layout.getName());
				return ps;
			}
		}, keyHolder);
		
		int layoutId = keyHolder.getKey().intValue();
		
		layout.setId(layoutId);
		addLayoutRoutes(layout);
	}
	
	@Transactional
	@Override
	public void update(Layout layout) {
		
		String sql = "UPDATE " +quoteTable("layout")+ " SET name = ? WHERE layout_id = ?";
		int num = getJdbcOperations().update(sql, layout.getName(), layout.getId());
		if(num>0){
			sql = "DELETE FROM "+quoteTable("layout_route")+ " WHERE layout_id=?";
			getJdbcOperations().update(sql, layout.getId());
			addLayoutRoutes(layout);
		}
	}
	
	private void addLayoutRoutes(Layout layout){
		if(layout.getLayoutRoutes().size()>0){
			String sql = "INSERT INTO " +quoteTable("layout_route")+ " SET layout_id = ?, store_id = ?, route = ?";
			for(LayoutRoute route: layout.getLayoutRoutes()){
				getJdbcOperations().update(sql, layout.getId(), layout.getId(), 
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
	public Layout get(Integer layoutId) {
		
		String sql = "SELECT DISTINCT * FROM " +quoteTable("layout")+ " WHERE layout_id =?";
		Layout layout = getJdbcOperations().queryForObject(sql, 
				new Object[]{layoutId}, new LayoutRowMapper());
		
		List<LayoutRoute> layoutRoutes = getLayoutRoutes(layoutId);
		layout.setLayoutRoutes(layoutRoutes);
		
		return layout;
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
		return getJdbcOperations().queryForInt(sql);
	}
	
	
}
