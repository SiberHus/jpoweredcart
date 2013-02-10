package com.jpoweredcart.admin.model.report.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import com.jpoweredcart.admin.bean.report.ProductsPurchased;
import com.jpoweredcart.admin.bean.report.ProductsViewed;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.admin.model.report.ProductsReportAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.service.SettingService;

public class ProductsReportAdminModelImpl extends BaseModel implements ProductsReportAdminModel {

	@Inject
	private SettingService settingService;
	
	public ProductsReportAdminModelImpl(SettingService settingService,
			JdbcOperations jdbcOperations) {
		super(settingService, jdbcOperations);
	}

	@Override
	public List<ProductsViewed> getProductsViewed(PageParam pageParam) {
		
		List<Object> params = new ArrayList<Object>();
		int languageId = settingService.getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		String sql = "SELECT pd.name, p.model, p.viewed FROM "+quoteTable("product")+" p LEFT JOIN " +
			quoteTable("product_description")+ " pd ON (p.product_id = pd.product_id) WHERE pd.language_id = ? AND p.viewed > 0 ORDER BY p.viewed DESC";
		params.add(languageId);
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		int totalProductViews = getTotalProductViews(); 
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new ProductsViewedRowMapper(totalProductViews));
	}

	@Override
	public int getTotalProductsViewed() {
		
		String sql = "SELECT COUNT(*) AS total FROM "+quoteTable("product")+" WHERE viewed > 0";
		return getJdbcOperations().queryForInt(sql);
	}
	
	@Override
	public int getTotalProductViews() {
		
		String sql = "SELECT SUM(viewed) AS total FROM "+quoteTable("product");
		return getJdbcOperations().queryForInt(sql);
	}
	
	@Transactional
	@Override
	public void resetViewed() {
		
		String sql = "UPDATE "+quoteTable("product")+" SET viewed = '0'";
		getJdbcOperations().update(sql);
	}

	@Override
	public List<ProductsPurchased> getProductsPurchased(
			DateRangeWithStatusFilter filter, PageParam pageParam) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT op.name, op.model, SUM(op.quantity) AS quantity, SUM(op.total + op.total * op.tax / 100) AS total FROM " +
				quoteTable("order_product")+ " op LEFT JOIN "+quoteTable("order")+" o ON (op.order_id = o.order_id)";
		if(filter.getStatusId()!=null && filter.getStatusId()>0){
			sql += " WHERE o.order_status_id = ?";
			params.add(filter.getStatusId());
		}else{
			sql +=" WHERE o.order_status_id > ?";
			params.add(0);
		}
		if(filter.getDateStart()!=null){
			sql += " AND o.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND o.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		sql += " GROUP BY op.model ORDER BY total DESC";
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new ProductsPurchasedRowMapper());
	}
	
	@Override
	public int getTotalProductsPurchased(DateRangeWithStatusFilter filter) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(DISTINCT op.model) AS total FROM "+quoteTable("order_product")+
				" op LEFT JOIN "+quoteTable("order")+" o ON (op.order_id = o.order_id)";
		if(filter.getStatusId()!=null && filter.getStatusId()>0){
			sql += " WHERE o.order_status_id = ?";
			params.add(filter.getStatusId());
		}else{
			sql +=" WHERE o.order_status_id > ?";
			params.add(0);
		}
		if(filter.getDateStart()!=null){
			sql += " AND o.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND o.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		
		return getJdbcOperations().queryForInt(sql, params.toArray());
	}
	
}
