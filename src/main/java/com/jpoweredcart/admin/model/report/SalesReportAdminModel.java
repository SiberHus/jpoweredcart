package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.SalesOrderByTitleReport;
import com.jpoweredcart.admin.bean.report.SalesOrderReport;
import com.jpoweredcart.admin.bean.report.SalesReportFilter;
import com.jpoweredcart.admin.bean.report.SalesReturnReport;
import com.jpoweredcart.common.PageParam;

public interface SalesReportAdminModel {
	
	public List<SalesOrderReport> getOrders(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalOrders(SalesReportFilter filter);
	
	public List<SalesOrderByTitleReport> getTaxes(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalTaxes(SalesReportFilter filter);
	
	public List<SalesOrderByTitleReport> getShippings(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalShippings(SalesReportFilter filter);
	
	public List<SalesReturnReport> getReturns(SalesReportFilter filter, PageParam pageParam);
	
	public int getTotalReturns(SalesReportFilter filter);
	
	
}
