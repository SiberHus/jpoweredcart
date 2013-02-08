package org.jpoweredcart.admin.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginAdminController {
	
	@RequestMapping(value="/admin/common/login", method=RequestMethod.GET)
	public String login(Model model){
		
		model.addAttribute("pagination1","#{text.pagination}");
		model.addAttribute("pagination2", "#messages.msg('msgKey')");
		return "/admin/common/login";
	}
	
}
