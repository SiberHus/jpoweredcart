package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.form.report.SaleCouponRpt;
import com.jpoweredcart.admin.form.report.SaleOrderByTitleRpt;
import com.jpoweredcart.admin.form.report.SaleOrderRpt;
import com.jpoweredcart.admin.form.report.SaleReturnRpt;
import com.jpoweredcart.admin.form.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.form.report.filter.SaleReportFilter;
import com.jpoweredcart.common.PageParam;

public interface SaleReportAdminModel {
	
	public List<SaleOrderRpt> getOrders(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalOrders(SaleReportFilter filter);
	
	public List<SaleOrderByTitleRpt> getTaxes(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalTaxes(SaleReportFilter filter);
	
	public List<SaleOrderByTitleRpt> getShippings(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalShippings(SaleReportFilter filter);
	
	public List<SaleReturnRpt> getReturns(SaleReportFilter filter, PageParam pageParam);
	
	public int getTotalReturns(SaleReportFilter filter);
	
	public List<SaleCouponRpt> getCoupons(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalCoupons(DateRangeFilter filter);
	
}
