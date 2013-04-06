package com.jpoweredcart.admin.controller.sale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.service.email.EmailService;

@Controller
@RequestMapping("/admin/sale/contact")
public class ContactAdminController extends BaseController {

	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private CustomerGroupAdminModel customerGroupAdminModel;
	
	@Inject
	private EmailService emailService;
	
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		model.addAttribute("stores", storeAdminModel.getAll());
		model.addAttribute("customerGroups", 
				customerGroupAdminModel.getList(PageParam.list()));
		
		return "/admin/sale/contact";
	}
	
	
}
