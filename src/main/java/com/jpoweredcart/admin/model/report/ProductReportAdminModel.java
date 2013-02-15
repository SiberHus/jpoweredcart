package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.ProductPurchased;
import com.jpoweredcart.admin.bean.report.ProductViewed;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.common.PageParam;

public interface ProductReportAdminModel {
	
	public List<ProductViewed> getProductsViewed(PageParam pageParam);
	
	public int getTotalProductsViewed();
	
	public int getTotalProductViews();
	
	public void resetViewed();
	
	public List<ProductPurchased> getProductsPurchased(DateRangeWithStatusFilter filter, PageParam pageParam);
	
	public int getTotalProductsPurchased(DateRangeWithStatusFilter filter);
	
}
