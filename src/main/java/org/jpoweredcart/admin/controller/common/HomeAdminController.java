package org.jpoweredcart.admin.controller.common;

import java.util.ArrayList;
import java.util.List;

import org.jpoweredcart.common.entity.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeAdminController {
	
	@RequestMapping(value="/admin/common/home")
	public String index(Model model){
		
		model.addAttribute("title", "Oh yeah6");
		model.addAttribute("base", "Fuck you");
		model.addAttribute("description", "N/A");
		List<Link> links = new ArrayList<Link>();
		links.add(Link.newCss("fuckfuck1"));
		model.addAttribute("links", links);
		model.addAttribute("logged", "Hussachai Puripunpinyo");
		return "/admin/common/home";
	}
	
}
