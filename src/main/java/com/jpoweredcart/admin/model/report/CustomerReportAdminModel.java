package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.form.report.CustomerCreditRpt;
import com.jpoweredcart.admin.form.report.CustomerOnlineRpt;
import com.jpoweredcart.admin.form.report.CustomerOrderRpt;
import com.jpoweredcart.admin.form.report.CustomerRewardRpt;
import com.jpoweredcart.admin.form.report.filter.CustomerOnlineFilter;
import com.jpoweredcart.admin.form.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.form.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.common.PageParam;

public interface CustomerReportAdminModel {
	
	public List<CustomerOrderRpt> getOrders(DateRangeWithStatusFilter filter, PageParam pageParam);
	
	public int getTotalOrders(DateRangeWithStatusFilter filter);
	
	public List<CustomerRewardRpt> getRewardPoints(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalRewardPoints(DateRangeFilter filter);
	
	public List<CustomerCreditRpt> getCredits(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalCredits(DateRangeFilter filter);
	
	public List<CustomerOnlineRpt> getOnlines(CustomerOnlineFilter filter, PageParam pageParam);
	
	public int getTotalOnlines(CustomerOnlineFilter filter);
	
}
