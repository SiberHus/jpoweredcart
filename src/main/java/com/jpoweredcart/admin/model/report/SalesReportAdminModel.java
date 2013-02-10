package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.SalesCoupon;
import com.jpoweredcart.admin.bean.report.SalesOrderByTitle;
import com.jpoweredcart.admin.bean.report.SalesOrder;
import com.jpoweredcart.admin.bean.report.SalesReturn;
import com.jpoweredcart.admin.bean.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.filter.SalesReportFilter;
import com.jpoweredcart.common.PageParam;

public interface SalesReportAdminModel {
	
	public List<SalesOrder> getOrders(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalOrders(SalesReportFilter filter);
	
	public List<SalesOrderByTitle> getTaxes(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalTaxes(SalesReportFilter filter);
	
	public List<SalesOrderByTitle> getShippings(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalShippings(SalesReportFilter filter);
	
	public List<SalesReturn> getReturns(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalReturns(SalesReportFilter filter);
	
	public List<SalesCoupon> getCoupons(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalCoupons(DateRangeFilter filter);
	
}
