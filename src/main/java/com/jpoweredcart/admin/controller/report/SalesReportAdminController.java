package com.jpoweredcart.admin.controller.report;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.admin.bean.report.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.SalesOrderByTitleReport;
import com.jpoweredcart.admin.bean.report.SalesOrderReport;
import com.jpoweredcart.admin.bean.report.SalesReportFilter;
import com.jpoweredcart.admin.bean.report.SalesReturnReport;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnStatusAdminModel;
import com.jpoweredcart.admin.model.report.SalesReportAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.view.Pagination;
import com.jpoweredcart.common.web.bridge.conversion.JQueryDateFormatTranslator;

@Controller
public class SalesReportAdminController extends BaseController {
	
	@Inject
	private SalesReportAdminModel salesReportAdminModel;
	
	@Inject
	private OrderStatusAdminModel orderStatusAdminModel;
	
	@Inject
	private ReturnStatusAdminModel returnStatusAdminModel;
	
	
	@RequestMapping(value="/admin/report/salesOrder")
	public String getOrders(SalesReportFilter filter, Model model, HttpServletRequest request){
		
		initDateFilter(filter, model, request);
		model.addAttribute("filter", filter);
		
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SalesOrderReport> orderList = salesReportAdminModel.getOrders(filter, pageParam);
		model.addAttribute("orders", orderList);
		int total = salesReportAdminModel.getTotalOrders(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/salesOrder"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/salesOrder";
	}
	
	@RequestMapping(value="/admin/report/salesTax")
	public String getTaxes(SalesReportFilter filter, Model model, HttpServletRequest request){
		
		initDateFilter(filter, model, request);
		model.addAttribute("filter", filter);
		
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SalesOrderByTitleReport> taxList = salesReportAdminModel.getTaxes(filter, pageParam);
		model.addAttribute("taxes", taxList);
		int total = salesReportAdminModel.getTotalTaxes(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/salesTax"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/salesTax";
	}
	
	@RequestMapping(value="/admin/report/salesShipping")
	public String getShippings(SalesReportFilter filter, Model model, HttpServletRequest request){
		
		initDateFilter(filter, model, request);
		model.addAttribute("filter", filter);
		
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SalesOrderByTitleReport> shippingList = salesReportAdminModel.getShippings(filter, pageParam);
		model.addAttribute("shippings", shippingList);
		int total = salesReportAdminModel.getTotalShippings(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/salesShipping"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/salesShipping";
	}
	
	@RequestMapping(value="/admin/report/salesReturn")
	public String getReturns(SalesReportFilter filter, Model model, HttpServletRequest request){
		
		initDateFilter(filter, model, request);
		model.addAttribute("filter", filter);
		
		model.addAttribute("returnStatuses", returnStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<SalesReturnReport> taxList = salesReportAdminModel.getReturns(filter, pageParam);
		model.addAttribute("returns", taxList);
		int total = salesReportAdminModel.getTotalReturns(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/salesReturn"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/salesReturn";
	}
	
	protected void initDateFilter(DateRangeFilter filter, Model model, HttpServletRequest request){
		if(filter.getDateStart()==null){
			Calendar current = Calendar.getInstance();
			current.set(Calendar.DAY_OF_MONTH, 1);
			filter.setDateStart(current.getTime());
		}
		if(filter.getDateEnd()==null){
			filter.setDateEnd(new Date());
		}
		
		String jsDateFormat = JQueryDateFormatTranslator.INSTANCE
				.translate(message(request, "date.formatShort"));
		model.addAttribute("jsDateFormat", jsDateFormat);
	}

	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}
	
}
