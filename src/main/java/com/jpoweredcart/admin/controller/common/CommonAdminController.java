package com.jpoweredcart.admin.controller.common;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jpoweredcart.common.service.template.TemplateService;
import com.jpoweredcart.common.view.helper.CurrencyFormatter;


@Controller
public class CommonAdminController {
	
	@Inject
	private TemplateService templateService;
	
	@RequestMapping(value={"/admin"})
	public String index(Model model){
		
		return "redirect:/admin/common/home";
	}
	
	@RequestMapping(value={"/admin/common/home"})
	public String dashboard(Model model, HttpServletRequest request){
		
		request.setAttribute("currency", new CurrencyFormatter());
		String template = templateService.renderTemplate("/admin/email/test", new HashMap<String, Object>());
		System.out.println(template);
		
		System.out.println(">>>>>>"+request.getRequestURL());
		
		return "/admin/common/home";
	}
	
}
