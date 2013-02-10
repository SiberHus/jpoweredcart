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

import com.jpoweredcart.admin.bean.report.ProductsPurchased;
import com.jpoweredcart.admin.bean.report.ProductsViewed;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.admin.model.report.ProductsReportAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.view.Pagination;

@Controller
public class ProductsReportAdminController extends BaseController {
	
	@Inject
	private ProductsReportAdminModel productsReportAdminModel;
	
	@Inject
	private OrderStatusAdminModel orderStatusAdminModel;
	
	@RequestMapping(value="/admin/report/productsViewed")
	public String getProductsViewed(Model model, HttpServletRequest request){
		
		addJsDateFormatAttribute(model, request);
		
		PageParam pageParam = createPageParam(request);
		List<ProductsViewed> viewedList = productsReportAdminModel.getProductsViewed(pageParam);
		model.addAttribute("productsViewed", viewedList);
		int total = productsReportAdminModel.getTotalProductsViewed();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/productsViewed"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/productsViewed";
	}
	
	@RequestMapping(value="/admin/report/productsViewed/reset", method=RequestMethod.POST)
	public String resetProductsViewed(Model model, HttpServletRequest request){
		
		productsReportAdminModel.resetViewed();
		
		return "redirect:/admin/report/productsViewed";
	}
	
	@RequestMapping(value="/admin/report/productsPurchased")
	public String getProductsPurchased(DateRangeWithStatusFilter filter, 
			Model model, HttpServletRequest request){
		
		addJsDateFormatAttribute(model, request);
		model.addAttribute("filter", filter);
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<ProductsPurchased> purchasedList = productsReportAdminModel.getProductsPurchased(filter, pageParam);
		model.addAttribute("productsPurchased", purchasedList);
		int total = productsReportAdminModel.getTotalProductsViewed();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/productsPurchased"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/productsPurchased";
	}

	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}
	
}
