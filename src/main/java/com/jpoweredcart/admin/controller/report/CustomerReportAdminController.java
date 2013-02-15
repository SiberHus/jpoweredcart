package com.jpoweredcart.admin.controller.report;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.admin.bean.report.CustomerCredit;
import com.jpoweredcart.admin.bean.report.CustomerOrder;
import com.jpoweredcart.admin.bean.report.CustomerReward;
import com.jpoweredcart.admin.bean.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.bean.report.filter.DateRangeWithStatusFilter;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import com.jpoweredcart.admin.model.report.CustomerReportAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.view.Pagination;

@Controller
public class CustomerReportAdminController extends BaseController {

	@Inject
	private CustomerReportAdminModel customerReportAdminModel;
	
	@Inject
	private OrderStatusAdminModel orderStatusAdminModel;
	
	@RequestMapping(value="/admin/report/customerOrder")
	public String getOrders(DateRangeWithStatusFilter filter, 
			Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		
		model.addAttribute("filter", filter);
		model.addAttribute("orderStatuses", orderStatusAdminModel.getList(null));
		
		PageParam pageParam = createPageParam(request);
		List<CustomerOrder> orderList = customerReportAdminModel
				.getOrders(filter, pageParam);
		model.addAttribute("orders", orderList);
		int total = customerReportAdminModel.getTotalOrders(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/customerOrder"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/customerOrder";
	}
	
	@RequestMapping(value="/admin/report/customerReward")
	public String getRewardPoints(DateRangeFilter filter, 
			Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		model.addAttribute("filter", filter);
		
		PageParam pageParam = createPageParam(request);
		List<CustomerReward> rewardList = customerReportAdminModel
				.getRewardPoints(filter, pageParam);
		model.addAttribute("rewards", rewardList);
		int total = customerReportAdminModel.getTotalRewardPoints(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/customerReward"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/customerReward";
	}
	
	@RequestMapping(value="/admin/report/customerCredit")
	public String getCredits(DateRangeFilter filter, 
			Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		model.addAttribute("filter", filter);
		
		PageParam pageParam = createPageParam(request);
		List<CustomerCredit> rewardList = customerReportAdminModel
				.getCredits(filter, pageParam);
		model.addAttribute("credits", rewardList);
		int total = customerReportAdminModel.getTotalCredits(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/customerCredit"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/customerCredit";
	}
	
	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}
}
