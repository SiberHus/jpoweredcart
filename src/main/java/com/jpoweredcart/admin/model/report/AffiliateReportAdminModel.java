package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.form.report.AffiliateCommissionRpt;
import com.jpoweredcart.admin.form.report.filter.DateRangeFilter;
import com.jpoweredcart.common.PageParam;

public interface AffiliateReportAdminModel {
	
	
	public List<AffiliateCommissionRpt> getCommissions(DateRangeFilter filter, PageParam pageParam);
	
	public int getTotalCommissions(DateRangeFilter filter);
	
}
