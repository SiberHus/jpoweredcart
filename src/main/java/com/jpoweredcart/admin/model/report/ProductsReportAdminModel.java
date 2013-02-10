package com.jpoweredcart.admin.model.report;

import java.util.List;

import com.jpoweredcart.admin.bean.report.ProductsPurchased;
import com.jpoweredcart.admin.bean.report.ProductsViewed;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.common.PageParam;

public interface ProductsReportAdminModel {
	
	public List<ProductsViewed> getProductsViewed(PageParam pageParam);
	
	public int getTotalProductsViewed();
	
	public int getTotalProductViews();
	
	public void resetViewed();
	
	public List<ProductsPurchased> getProductsPurchased(DateRangeWithStatusFilter filter, PageParam pageParam);
	
	public int getTotalProductsPurchased(DateRangeWithStatusFilter filter);
	
}
