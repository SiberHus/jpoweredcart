package com.jpoweredcart.admin.model.report.jdbc;

import java.util.ArrayList;
import java.util.List;

import com.jpoweredcart.admin.bean.report.CustomerCredit;
import com.jpoweredcart.admin.bean.report.CustomerOrder;
import com.jpoweredcart.admin.bean.report.CustomerReward;
import com.jpoweredcart.admin.bean.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.admin.model.report.CustomerReportAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.service.SettingKey;

public class CustomerReportAdminModelImpl extends BaseModel implements CustomerReportAdminModel {

	@Override
	public List<CustomerOrder> getOrders(DateRangeWithStatusFilter filter,
			PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT c.customer_id, c.firstname, c.lastname, c.email, cgd.name AS customer_group, c.status, " +
				"COUNT(o.order_id) AS orders, SUM(op.quantity) AS products, SUM(o.total) AS total FROM "+quoteTable("order")+
				" o LEFT JOIN "+quoteTable("order_product")+" op ON (o.order_id = op.order_id)LEFT JOIN "+quoteTable("customer")+
				" c ON (o.customer_id = c.customer_id) LEFT JOIN "+quoteTable("customer_group_description")+
				" cgd ON (c.customer_group_id = cgd.customer_group_id) WHERE o.customer_id > 0 AND cgd.language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		params.add(languageId);
		if(filter.getStatusId()!=null && filter.getStatusId()>0){
			sql += " AND o.order_status_id = ?";
			params.add(filter.getStatusId());
		}else{
			sql +=" AND o.order_status_id > ?";
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
		sql +=  " GROUP BY o.customer_id ORDER BY total DESC";
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new CustomerOrderRowMapper());
	}
	
	@Override
	public int getTotalOrders(DateRangeWithStatusFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(DISTINCT o.customer_id) AS total FROM "+
				quoteTable("order")+" o WHERE o.customer_id > '0'";
		if(filter.getStatusId()!=null && filter.getStatusId()>0){
			sql += " AND o.order_status_id = ?";
			params.add(filter.getStatusId());
		}else{
			sql +=" AND o.order_status_id > ?";
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

	@Override
	public List<CustomerReward> getRewardPoints(DateRangeFilter filter,
			PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT cr.customer_id, c.firstname, c.lastname, c.email, cgd.name AS customer_group, " +
				"c.status, SUM(cr.points) AS points, COUNT(o.order_id) AS orders, SUM(o.total) AS total FROM "+
				quoteTable("customer_reward")+" cr LEFT JOIN "+quoteTable("customer")+
				" c ON (cr.customer_id = c.customer_id) LEFT JOIN "+quoteTable("customer_group_description")+
				" cgd ON (c.customer_group_id = cgd.customer_group_id) LEFT JOIN "+quoteTable("order")+
				" o ON (cr.order_id = o.order_id) WHERE cgd.language_id=?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		params.add(languageId);
		if(filter.getDateStart()!=null){
			sql += " AND cr.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND cr.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		sql += " GROUP BY cr.customer_id ORDER BY points DESC";
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new CustomerRewardRowMapper());
	}
	
	@Override
	public int getTotalRewardPoints(DateRangeFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(DISTINCT customer_id) AS total FROM "+
				quoteTable("customer_reward")+" WHERE 1=1 ";
		if(filter.getDateStart()!=null){
			sql += " AND date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND date_added <= ?";
			params.add(filter.getDateEnd());
		}
		
		return getJdbcOperations().queryForInt(sql, params.toArray());
	}

	@Override
	public List<CustomerCredit> getCredits(DateRangeFilter filter, PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT ct.customer_id, c.firstname, c.lastname, c.email, cgd.name AS customer_group, c.status, " +
				"SUM(ct.amount) AS total FROM "+quoteTable("customer_transaction")+" ct LEFT JOIN "+quoteTable("customer")+
				" c ON (ct.customer_id = c.customer_id) LEFT JOIN "+quoteTable("customer_group_description")+
				" cgd ON (c.customer_group_id = cgd.customer_group_id) WHERE cgd.language_id = ?";
		Integer languageId = getSettingService().getConfig(SettingKey.ADMIN_LANGUAGE_ID, Integer.class);
		params.add(languageId);
		if(filter.getDateStart()!=null){
			sql += " AND ct.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND ct.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		sql += " GROUP BY ct.customer_id ORDER BY total DESC";
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new CustomerCreditRowMapper());
	}

	@Override
	public int getTotalCredits(DateRangeFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(DISTINCT customer_id) AS total FROM "+
				quoteTable("customer_transaction")+" WHERE 1=1 ";
		if(filter.getDateStart()!=null){
			sql += " AND date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND date_added <= ?";
			params.add(filter.getDateEnd());
		}
		
		return getJdbcOperations().queryForInt(sql, params.toArray());
	}
	
	
}
