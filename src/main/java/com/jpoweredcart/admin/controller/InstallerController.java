package com.jpoweredcart.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/install")
public class InstallerController {
	
	@RequestMapping("/step1")
	public String step1(){
		
		return "/admin/install/step1";
	}
	
	@RequestMapping("/step2")
	public String step2(){
		
		return "/admin/install/step2";
	}
	
	@RequestMapping("/step3")
	public String step3(){
		
		return "/admin/install/step3";
	}
	
}
