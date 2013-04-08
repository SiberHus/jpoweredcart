package com.jpoweredcart.admin.controller.sale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jpoweredcart.admin.bean.sale.ContactForm;
import com.jpoweredcart.admin.bean.sale.EmailSubmissionResult;
import com.jpoweredcart.admin.model.sale.ContactAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;

@Controller
@RequestMapping("/admin/sale/contact")
public class ContactAdminController extends BaseController {

	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private CustomerGroupAdminModel customerGroupAdminModel;
	
	@Inject
	private ContactAdminModel contactAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		model.addAttribute("stores", storeAdminModel.getAll());
		model.addAttribute("customerGroups", 
				customerGroupAdminModel.getList(PageParam.list()));
		
		return "/admin/sale/contact";
	}
	
	@RequestMapping(value="/send", method=RequestMethod.POST)
	public @ResponseBody EmailSubmissionResult send(@Valid ContactForm contactForm, Model model){
		
		EmailSubmissionResult result = new EmailSubmissionResult();
		result.addError("subject", "subject is required");
		return result;
	}
	
}
