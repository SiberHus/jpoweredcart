package com.jpoweredcart.admin.controller.common;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import com.jpoweredcart.admin.form.sale.filter.AffiliateFilter;
import com.jpoweredcart.admin.form.sale.filter.CustomerFilter;
import com.jpoweredcart.admin.form.sale.filter.TotalOrdersFilter;
import com.jpoweredcart.admin.model.catalog.ReviewAdminModel;
import com.jpoweredcart.admin.model.sale.AffiliateAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerAdminModel;
import com.jpoweredcart.admin.model.sale.OrderAdminModel;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.UserAttributes;
import com.jpoweredcart.common.service.CurrencyService;


@Controller
public class CommonAdminController {
	
	@Inject
	private OrderAdminModel orderAdminModel;
	
	@Inject
	private CustomerAdminModel customerAdminModel;
	
	@Inject
	private ReviewAdminModel reviewAdminModel;
	
	@Inject
	private AffiliateAdminModel affiliateAdminModel;
	
	@Inject
	private CurrencyService currencyService;
	
	@RequestMapping(value={"/admin"})
	public String index(Model model){
		
		return "redirect:/admin/common/home";
	}
	
	@RequestMapping(value={"/admin/common/home"})
	public String dashboard(Model model, HttpServletRequest request){
		
		CurrencyService c = currencyService;
		
		UserAttributes userAttrs = (UserAttributes)WebUtils.getSessionAttribute(request, UserAttributes.NAME);
		Locale locale = userAttrs.getLocale();
		String currencyCode = userAttrs.getCurrency();
		/* Current year */
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		model.addAttribute("totalSale", c.format(
				orderAdminModel.getTotalSales(), currencyCode, locale));
		model.addAttribute("totalSaleYear", c.format(
				orderAdminModel.getTotalSalesByYear(year), currencyCode, locale));
		
		model.addAttribute("totalOrder", orderAdminModel.getTotal(new TotalOrdersFilter()));
		
		model.addAttribute("totalCustomer", customerAdminModel.getTotal(new CustomerFilter()));
		model.addAttribute("totalCustomerApproval", customerAdminModel.getTotalAwaitingApproval());
		model.addAttribute("totalReview", reviewAdminModel.getTotal());
		model.addAttribute("totalReviewApproval", reviewAdminModel.getTotalAwaitingApproval());
		model.addAttribute("totalAffiliate", affiliateAdminModel.getTotal(new AffiliateFilter()));
		model.addAttribute("totalAffiliateApproval", affiliateAdminModel.getTotalAwaitingApproval());
		
		PageParam orderListParam = PageParam.list(0, 10);
		orderListParam.addOrder("o.date_added", "DESC");
		model.addAttribute("orders", orderAdminModel.getList(orderListParam));
		
		return "/admin/common/home";
	}
	
}
