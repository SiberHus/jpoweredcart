package com.jpoweredcart.admin.controller.report;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jpoweredcart.admin.bean.report.ProductPurchasedRpt;
import com.jpoweredcart.admin.bean.report.ProductViewedRpt;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.admin.model.report.ProductReportAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.view.Pagination;

@Controller
public class ProductReportAdminController extends BaseController {
	
	@Inject
	private ProductReportAdminModel productReportAdminModel;
	
	@Inject
	private OrderStatusAdminModel orderStatusAdminModel;
	
	@RequestMapping(value="/admin/report/productViewed")
	public String getProductsViewed(Model model, HttpServletRequest request){
		
		addJsDateFormatAttribute(model, request);
		
		PageParam pageParam = createPageParam(request);
		List<ProductViewedRpt> viewedList = productReportAdminModel.getProductsViewed(pageParam);
		model.addAttribute("productsViewed", viewedList);
		int total = productReportAdminModel.getTotalProductsViewed();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/productViewed"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/productViewed";
	}
	
	@RequestMapping(value="/admin/report/productViewed/reset", method=RequestMethod.POST)
	public String resetProductsViewed(Model model, HttpServletRequest request){
		
		productReportAdminModel.resetViewed();
		
		return "redirect:/admin/report/productViewed";
	}
	
	@RequestMapping(value="/admin/report/productPurchased")
	public String getProductsPurchased(DateRangeWithStatusFilter filter, 
			Model model, HttpServletRequest request){
		
		addJsDateFormatAttribute(model, request);
		model.addAttribute("filter", filter);
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<ProductPurchasedRpt> purchasedList = productReportAdminModel.getProductsPurchased(filter, pageParam);
		model.addAttribute("productsPurchased", purchasedList);
		int total = productReportAdminModel.getTotalProductsViewed();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/productPurchased"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/productPurchased";
	}

	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}
	
}
