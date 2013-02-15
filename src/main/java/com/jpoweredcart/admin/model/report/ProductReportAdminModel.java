package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.ProductPurchasedRpt;
import com.jpoweredcart.admin.bean.report.ProductViewedRpt;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.common.PageParam;

public interface ProductReportAdminModel {
	
	public List<ProductViewedRpt> getProductsViewed(PageParam pageParam);
	
	public int getTotalProductsViewed();
	
	public int getTotalProductViews();
	
	public void resetViewed();
	
	public List<ProductPurchasedRpt> getProductsPurchased(DateRangeWithStatusFilter filter, PageParam pageParam);
	
	public int getTotalProductsPurchased(DateRangeWithStatusFilter filter);
	
}
