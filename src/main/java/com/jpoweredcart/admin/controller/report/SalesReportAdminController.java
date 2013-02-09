package com.jpoweredcart.admin.controller.report;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.admin.bean.report.SalesOrderReport;
import com.jpoweredcart.admin.bean.report.SalesOrderReportFilter;
import com.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
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
	
	@RequestMapping(value="/admin/report/salesOrder")
	public String getOrders(SalesOrderReportFilter filter, Model model, HttpServletRequest request){
		if(filter.getDateStart()==null){
			Calendar current = Calendar.getInstance();
			current.set(Calendar.DAY_OF_MONTH, 1);
			filter.setDateStart(current.getTime());
		}
		if(filter.getDateEnd()==null){
			filter.setDateEnd(new Date());
		}
		model.addAttribute("filter", filter);
		String jsDateFormat = JQueryDateFormatTranslator.INSTANCE.translate(message(request, "date.formatShort"));
		model.addAttribute("jsDateFormat", jsDateFormat);
		
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
	
	
}
