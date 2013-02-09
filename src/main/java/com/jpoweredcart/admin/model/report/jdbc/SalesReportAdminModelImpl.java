package com.jpoweredcart.admin.model.report.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcOperations;

import com.jpoweredcart.admin.bean.report.SalesOrderReport;
import com.jpoweredcart.admin.bean.report.SalesOrderReportFilter;
import com.jpoweredcart.admin.model.report.SalesReportAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;
import com.jpoweredcart.common.service.SettingService;

public class SalesReportAdminModelImpl extends BaseModel implements SalesReportAdminModel{
	
	
	public SalesReportAdminModelImpl(SettingService settingService,
			JdbcOperations jdbcOperations) {
		super(settingService, jdbcOperations);
	}
	
	@Override
	public List<SalesOrderReport> getOrders(SalesOrderReportFilter filter, PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT MIN(tmp.date_added) AS date_start, MAX(tmp.date_added) AS date_end, " +
			"COUNT(tmp.order_id) AS orders, SUM(tmp.products) AS products, SUM(tmp.tax) AS tax, " +
			"SUM(tmp.total) AS total FROM (SELECT o.order_id, (SELECT SUM(op.quantity) FROM "+
			quoteTable("order_product")+" op WHERE op.order_id = o.order_id GROUP BY op.order_id) AS products, " +
			"(SELECT SUM(ot.value) FROM "+quoteTable("order_total")+" ot WHERE ot.order_id = o.order_id " +
			"AND ot.code = 'tax' GROUP BY ot.order_id) AS tax, o.total, o.date_added FROM " +quoteTable("order")+ " o";
		if(filter.getOrderStatusId()!=null && filter.getOrderStatusId()>0){
			sql += " WHERE o.order_status_id = ?";
			params.add(filter.getOrderStatusId());
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
		sql += " GROUP BY o.order_id) tmp";
		
		String group = null;
		if(StringUtils.isNotBlank(filter.getGroup())){
			group = filter.getGroup();
		}else{
			group = "week";
		}
		if(group.equals("day")){
			sql += " GROUP BY DAY(tmp.date_added)";
		}else if(group.equals("week")){
			sql += " GROUP BY WEEK(tmp.date_added)";
		}else if(group.equals("month")){
			sql += " GROUP BY MONTH(tmp.date_added)";
		}else if(group.equals("year")){
			sql += " GROUP BY YEAR(tmp.date_added)";
		}
		sql += " ORDER BY tmp.date_added DESC";
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new SalesOrderReportRowMapper());
	}
	
	@Override
	public int getTotalOrders(SalesOrderReportFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = null;
		String group = null;
		if(StringUtils.isNotBlank(filter.getGroup())){
			group = filter.getGroup();
		}else{
			group = "week";
		}
		if(group.equals("day")){
			sql = "SELECT COUNT(DISTINCT DAY(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}else if(group.equals("week")){
			sql = "SELECT COUNT(DISTINCT WEEK(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}else if(group.equals("month")){
			sql = "SELECT COUNT(DISTINCT MONTH(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}else if(group.equals("year")){
			sql = "SELECT COUNT(DISTINCT YEAR(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}
		
		if(filter.getOrderStatusId()!=null && filter.getOrderStatusId()>0){
			sql += " WHERE o.order_status_id = ?";
			params.add(filter.getOrderStatusId());
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
