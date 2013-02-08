package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.SalesOrderReport;
import com.jpoweredcart.admin.bean.report.SalesOrderReportFilter;

public interface SalesReportAdminModel {
	
	public List<SalesOrderReport> getOrders(SalesOrderReportFilter filter);
	
}
