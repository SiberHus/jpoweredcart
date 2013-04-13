package com.jpoweredcart.admin.controller.report;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.admin.form.report.AffiliateCommissionRpt;
import com.jpoweredcart.admin.form.report.filter.DateRangeFilter;
import com.jpoweredcart.admin.model.report.AffiliateReportAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.view.Pagination;

@Controller
public class AffiliateReportAdminController extends BaseController {
	
	@Inject
	private AffiliateReportAdminModel affiliateReportAdminModel;
	
	@RequestMapping(value="/admin/report/affiliateCommission")
	public String getCommissions(DateRangeFilter filter, 
			Model model, HttpServletRequest request){
		
		filter.setDefaultDateRange();
		addJsDateFormatAttribute(model, request);
		model.addAttribute("filter", filter);
		
		PageParam pageParam = createPageParam(request);
		List<AffiliateCommissionRpt> commissionList = affiliateReportAdminModel
				.getCommissions(filter, pageParam);
		model.addAttribute("commissions", commissionList);
		int total = affiliateReportAdminModel.getTotalCommissions(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/report/affiliateCommission"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/report/affiliateCommission";
	}

	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}
	
	
}
