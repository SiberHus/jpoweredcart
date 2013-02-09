package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.SalesOrderReport;
import com.jpoweredcart.admin.bean.report.SalesOrderTitleReport;
import com.jpoweredcart.admin.bean.report.SalesReportFilter;
import com.jpoweredcart.common.PageParam;

public interface SalesReportAdminModel {
	
	public List<SalesOrderReport> getOrders(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalOrders(SalesReportFilter filter);
	
	public List<SalesOrderTitleReport> getTaxes(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalTaxes(SalesReportFilter filter);
	
	public List<SalesOrderTitleReport> getShippings(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalShippings(SalesReportFilter filter);
	
}
