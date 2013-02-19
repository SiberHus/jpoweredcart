package com.jpoweredcart.admin.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/common/fileManager")
public class FileManagerAdminController {
	
	@RequestMapping(value={"","/"})
	public String index(Model model, HttpServletRequest request){
		
		return "/admin/common/fileManager";
	}
}
