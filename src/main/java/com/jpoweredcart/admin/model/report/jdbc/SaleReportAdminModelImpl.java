package com.jpoweredcart.admin.model.report.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jpoweredcart.admin.bean.report.SaleCoupon;
import com.jpoweredcart.admin.bean.report.SaleOrder;
import com.jpoweredcart.admin.bean.report.SaleOrderByTitle;
import com.jpoweredcart.admin.bean.report.SaleReturn;
import com.jpoweredcart.admin.bean.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.filter.SaleReportFilter;
import com.jpoweredcart.admin.model.report.SaleReportAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.QueryBean;

public class SaleReportAdminModelImpl extends BaseModel implements SaleReportAdminModel{
	
	
	@Override
	public List<SaleOrder> getOrders(SaleReportFilter filter, PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT MIN(tmp.date_added) AS date_start, MAX(tmp.date_added) AS date_end, " +
			"COUNT(tmp.order_id) AS orders, SUM(tmp.products) AS products, SUM(tmp.tax) AS tax, " +
			"SUM(tmp.total) AS total FROM (SELECT o.order_id, (SELECT SUM(op.quantity) FROM "+
			quoteTable("order_product")+" op WHERE op.order_id = o.order_id GROUP BY op.order_id) AS products, " +
			"(SELECT SUM(ot.value) FROM "+quoteTable("order_total")+" ot WHERE ot.order_id = o.order_id " +
			"AND ot.code = 'tax' GROUP BY ot.order_id) AS tax, o.total, o.date_added FROM " +quoteTable("order")+ " o";
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
		sql += " GROUP BY o.order_id) tmp";
		
		String group = getFilterGroup(filter);
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
				params.toArray(), new SaleOrderRowMapper());
	}
	
	@Override
	public int getTotalOrders(SaleReportFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = null;
		String group = getFilterGroup(filter);
		if(group.equals("day")){
			sql = "SELECT COUNT(DISTINCT DAY(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}else if(group.equals("week")){
			sql = "SELECT COUNT(DISTINCT WEEK(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}else if(group.equals("month")){
			sql = "SELECT COUNT(DISTINCT MONTH(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}else if(group.equals("year")){
			sql = "SELECT COUNT(DISTINCT YEAR(date_added)) AS total FROM "+quoteTable("order")+ " o";
		}
		
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

	@Override
	public List<SaleOrderByTitle> getTaxes(SaleReportFilter filter,
			PageParam pageParam) {
		
		return getSalesOrderTitles(filter, "tax", pageParam);
	}

	@Override
	public int getTotalTaxes(SaleReportFilter filter) {
		
		return getTotalSalesOrderTitles(filter, "tax");
	}
	
	
	@Override
	public List<SaleOrderByTitle> getShippings(SaleReportFilter filter,
			PageParam pageParam) {
		
		return getSalesOrderTitles(filter, "shipping", pageParam);
	}
	
	@Override
	public int getTotalShippings(SaleReportFilter filter) {
		
		return getTotalSalesOrderTitles(filter, "shipping");
	}
	
	protected List<SaleOrderByTitle> getSalesOrderTitles(SaleReportFilter filter, String code,
			PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT MIN(o.date_added) AS date_start, MAX(o.date_added) AS date_end, ot.title, " +
			"SUM(ot.value) AS total, COUNT(o.order_id) AS orders FROM "+quoteTable("order_total")+ 
			" ot LEFT JOIN "+quoteTable("order")+" o ON (ot.order_id = o.order_id) WHERE ot.code = ?";
		params.add(code);
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
		String group = getFilterGroup(filter);
		if(group.equals("day")){
			sql += " GROUP BY ot.title, DAY(o.date_added)";
		}else if(group.equals("week")){
			sql += " GROUP BY ot.title, WEEK(o.date_added)";
		}else if(group.equals("month")){
			sql += " GROUP BY ot.title, MONTH(o.date_added)";
		}else if(group.equals("year")){
			sql += " GROUP BY ot.title, YEAR(o.date_added)";
		}
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new SaleOrderByTitleRowMapper());
	}
	
	protected int getTotalSalesOrderTitles(SaleReportFilter filter, String code) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(*) AS total FROM (SELECT COUNT(*) AS total FROM "+
					quoteTable("order_total")+" ot LEFT JOIN "+quoteTable("order")+
					" o ON (ot.order_id = o.order_id) WHERE ot.code = ?";
		params.add(code);
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
		String group = getFilterGroup(filter);
		if(group.equals("day")){
			sql += " GROUP BY DAY(o.date_added), ot.title";
		}else if(group.equals("week")){
			sql += " GROUP BY WEEK(o.date_added), ot.title";
		}else if(group.equals("month")){
			sql += " GROUP BY MONTH(o.date_added), ot.title";
		}else if(group.equals("year")){
			sql += " GROUP BY YEAR(o.date_added), ot.title";
		}
		sql += ") tmp";
		
		return getJdbcOperations().queryForInt(sql, params.toArray());
	}
	
	@Override
	public List<SaleReturn> getReturns(SaleReportFilter filter,
			PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT MIN(r.date_added) AS date_start, MAX(r.date_added) AS date_end, " +
				"COUNT(r.return_id) AS returns FROM "+quoteTable("return")+" r";
		if(filter.getStatusId()!=null && filter.getStatusId()>0){
			sql += " WHERE r.return_status_id = ?";
			params.add(filter.getStatusId());
		}else{
			sql += " WHERE r.return_status_id > ?";
			params.add(0);
		}
		if(filter.getDateStart()!=null){
			sql += " AND r.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += " AND r.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		String group = getFilterGroup(filter);
		if(group.equals("day")){
			sql += " GROUP BY DAY(r.date_added)";
		}else if(group.equals("week")){
			sql += " GROUP BY WEEK(r.date_added)";
		}else if(group.equals("month")){
			sql += " GROUP BY MONTH(r.date_added)";
		}else if(group.equals("year")){
			sql += " GROUP BY YEAR(r.date_added)";
		}
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new SaleReturnRowMapper());
	}
	
	@Override
	public int getTotalReturns(SaleReportFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = null;
		String group = getFilterGroup(filter);
		if(group.equals("day")){
			sql = "SELECT COUNT(DISTINCT DAY(date_added)) AS total FROM "+quoteTable("return");
		}else if(group.equals("week")){
			sql = "SELECT COUNT(DISTINCT WEEK(date_added)) AS total FROM "+quoteTable("return");
		}else if(group.equals("month")){
			sql = "SELECT COUNT(DISTINCT MONTH(date_added)) AS total FROM "+quoteTable("return");
		}else if(group.equals("year")){
			sql = "SELECT COUNT(DISTINCT YEAR(date_added)) AS total FROM "+quoteTable("return");
		}
		
		if(filter.getStatusId()!=null && filter.getStatusId()>0){
			sql += " WHERE return_status_id = ?";
			params.add(filter.getStatusId());
		}else{
			sql += " WHERE return_status_id > ?";
			params.add(0);
		}
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
	public List<SaleCoupon> getCoupons(DateRangeFilter filter,
			PageParam pageParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT ch.coupon_id, c.name, c.code, COUNT(DISTINCT ch.order_id) AS `orders`, SUM(ch.amount) AS total FROM "+
			quoteTable("coupon_history")+" ch LEFT JOIN "+quoteTable("coupon")+" c ON (ch.coupon_id = c.coupon_id) WHERE 1=1 ";
		if(filter.getDateStart()!=null){
			sql += "AND c.date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += "AND c.date_added <= ?";
			params.add(filter.getDateEnd());
		}
		sql += " GROUP BY ch.coupon_id ORDER BY total DESC";
		
		QueryBean query = createPaginationQueryFromSql(sql, pageParam);
		params.add(query.getStart());
		params.add(query.getLimit());
		
		return getJdbcOperations().query(query.getSql(), 
				params.toArray(), new SaleCouponRowMapper());
	}

	@Override
	public int getTotalCoupons(DateRangeFilter filter) {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT COUNT(DISTINCT coupon_id) AS total FROM "+quoteTable("coupon_history")+" WHERE 1=1 ";
		if(filter.getDateStart()!=null){
			sql += "AND date_added >= ?";
			params.add(filter.getDateStart());
		}
		if(filter.getDateEnd()!=null){
			sql += "AND date_added <= ?";
			params.add(filter.getDateEnd());
		}
		
		return getJdbcOperations().queryForInt(sql, params.toArray());
	}
	
	private String getFilterGroup(SaleReportFilter filter){
		
		if(StringUtils.isNotBlank(filter.getGroup())){
			return filter.getGroup();
		}else{
			return "week";
		}
	}
}
