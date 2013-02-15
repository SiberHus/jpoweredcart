package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.SaleCoupon;
import com.jpoweredcart.admin.bean.report.SaleOrderByTitle;
import com.jpoweredcart.admin.bean.report.SaleOrder;
import com.jpoweredcart.admin.bean.report.SaleReturn;
import com.jpoweredcart.admin.bean.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.filter.SaleReportFilter;
import com.jpoweredcart.common.PageParam;

public interface SaleReportAdminModel {
	
	public List<SaleOrder> getOrders(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalOrders(SaleReportFilter filter);
	
	public List<SaleOrderByTitle> getTaxes(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalTaxes(SaleReportFilter filter);
	
	public List<SaleOrderByTitle> getShippings(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalShippings(SaleReportFilter filter);
	
	public List<SaleReturn> getReturns(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalReturns(SaleReportFilter filter);
	
	public List<SaleCoupon> getCoupons(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalCoupons(DateRangeFilter filter);
	
}
