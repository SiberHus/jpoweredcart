package com.jpoweredcart.admin.model.report.impl;

import java.util.ArrayList;
import java.util.List;

import com.jpoweredcart.admin.form.report.AffiliateCommissionRpt;
import com.jpoweredcart.admin.form.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.model.report.AffiliateReportAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.entity.report.jdbc.AffiliateCommissionRptRowMapper;

public class AffiliateReportAdminModelImpl extends BaseModel implements AffiliateReportAdminModel {

	@Override
	public List<AffiliateCommissionRpt> getCommissions(DateRangeFilter filter,
			PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT at.affiliate_id, a.firstname, a.lastname, a.email, a.status, " +
				"SUM(at.amount) AS commission, COUNT(o.order_id) AS orders, SUM(o.total) AS total FROM "+
				quoteTable("affiliate_transaction")+" at LEFT JOIN "+quoteTable("affiliate")+
				" a ON (at.affiliate_id = a.affiliate_id) LEFT JOIN "+quoteTable("order")+
				" o ON (at.order_id = o.order_id) WHERE 1=1 ";
		
		if(filter.getDateStart()!=null){
			sql += "AND at.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += "AND at.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		sql += " GROUP BY at.affiliate_id ORDER BY commission DESC";
		
		QueryBean query = createPaginationQuery(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new AffiliateCommissionRptRowMapper());
	}

	@Override
	public int getTotalCommissions(DateRangeFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(DISTINCT affiliate_id) AS total FROM "+
				quoteTable("affiliate_transaction")+" WHERE 1=1 ";
		if(filter.getDateStart()!=null){
			sql += "AND date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += "AND date_added <= ?";
			params.add(filter.getDateEnd());
		}
		
		return getJdbcOperations().queryForObject(sql, Integer.class, params.toArray());
	}
	
}
