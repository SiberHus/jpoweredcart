package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.CustomerCredit;
import com.jpoweredcart.admin.bean.report.CustomerOrder;
import com.jpoweredcart.admin.bean.report.CustomerReward;
import com.jpoweredcart.admin.bean.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.common.PageParam;

public interface CustomerReportAdminModel {
	
	public List<CustomerOrder> getOrders(DateRangeWithStatusFilter filter, PageParam pageParam);
	
	public int getTotalOrders(DateRangeWithStatusFilter filter);
	
	public List<CustomerReward> getRewardPoints(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalRewardPoints(DateRangeFilter filter);
	
	public List<CustomerCredit> getCredits(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalCredits(DateRangeFilter filter);
	
}
