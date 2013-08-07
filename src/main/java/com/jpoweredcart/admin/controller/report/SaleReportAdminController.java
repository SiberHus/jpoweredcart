package com.jpoweredcart.admin.controller.report;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.admin.form.report.SaleCouponRpt;
import com.jpoweredcart.admin.form.report.SaleOrderByTitleRpt;
import com.jpoweredcart.admin.form.report.SaleOrderRpt;
import com.jpoweredcart.admin.form.report.SaleReturnRpt;
import com.jpoweredcart.admin.form.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.form.report.filter.SaleReportFilter;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnStatusAdminModel;
import com.jpoweredcart.admin.model.report.SaleReportAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.view.Pagination;

@Controller
public class SaleReportAdminController extends BaseController {
	
	@Inject
	private SaleReportAdminModel saleReportAdminModel;
	
	@Inject
	private OrderStatusAdminModel orderStatusAdminModel;
	
	@Inject
	private ReturnStatusAdminModel returnStatusAdminModel;
	
	
	@RequestMapping(value="/admin/report/saleOrder")
	public String getOrders(SaleReportFilter filter, Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		
		model.addAttribute("filter", filter);
		
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SaleOrderRpt> orderList = saleReportAdminModel.getOrders(filter, pageParam);
		model.addAttribute("orders", orderList);
		int total = saleReportAdminModel.getTotalOrders(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/saleOrder"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/saleOrder";
	}
	
	@RequestMapping(value="/admin/report/saleTax")
	public String getTaxes(SaleReportFilter filter, Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		
		model.addAttribute("filter", filter);
		
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SaleOrderByTitleRpt> taxList = saleReportAdminModel.getTaxes(filter, pageParam);
		model.addAttribute("taxes", taxList);
		int total = saleReportAdminModel.getTotalTaxes(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/saleTax"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/saleTax";
	}
	
	@RequestMapping(value="/admin/report/saleShipping")
	public String getShippings(SaleReportFilter filter, Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		
		model.addAttribute("filter", filter);
		
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SaleOrderByTitleRpt> shippingList = saleReportAdminModel.getShippings(filter, pageParam);
		model.addAttribute("shippings", shippingList);
		int total = saleReportAdminModel.getTotalShippings(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/saleShipping"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/saleShipping";
	}
	
	@RequestMapping(value="/admin/report/saleReturn")
	public String getReturns(SaleReportFilter filter, Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		
		model.addAttribute("filter", filter);
		
		model.addAttribute("returnStatuses", returnStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SaleReturnRpt> taxList = saleReportAdminModel.getReturns(filter, pageParam);
		model.addAttribute("returns", taxList);
		int total = saleReportAdminModel.getTotalReturns(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/saleReturn"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/saleReturn";
	}
	
	@RequestMapping(value="/admin/report/saleCoupon")
	public String getCoupons(DateRangeFilter filter, Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		
		model.addAttribute("filter", filter);
		
		PageParam pageParam = createPageParam(request);
		List<SaleCouponRpt> taxList = saleReportAdminModel.getCoupons(filter, pageParam);
		model.addAttribute("coupons", taxList);
		int total = saleReportAdminModel.getTotalCoupons(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/saleReturn"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/saleCoupon";
	}
	
	
	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}
	
}
