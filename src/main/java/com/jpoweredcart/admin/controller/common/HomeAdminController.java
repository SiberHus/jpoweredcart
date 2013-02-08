package org.jpoweredcart.admin.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jpoweredcart.common.web.Link;
import org.jpoweredcart.common.web.mock.MockHttpServletRequest;
import org.jpoweredcart.common.web.mock.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;


@Controller
public class HomeAdminController {
	
	@Inject
	private ViewResolver viewResolver;
	
	@Inject
	private SpringTemplateEngine templateEngine;
	
	@Inject
	private TemplateResolver templateResolver;
	
	@RequestMapping(value={"/admin"})
	public String index(Model model){
		
		return "redirect:/admin/common/home";
	}
	
	@RequestMapping(value={"/admin/common/home"})
	public String dashboard(Model model, HttpServletRequest request){
		
		model.addAttribute("title", "Oh yeah6");
		model.addAttribute("base", "Fuck you");
		model.addAttribute("description", "N/A");
		List<Link> links = new ArrayList<Link>();
		links.add(Link.newCss("fuckfuck1"));
		model.addAttribute("links", links);
		model.addAttribute("logged", "Hussachai Puripunpinyo");
		
//		final String contextPath = request.getContextPath();
//		Thread t = new Thread(){
//			@Override
//			public void run(){
//				try{
//					Thread.sleep(5*1000);
//				}catch(Exception e){}
//				MockHttpServletRequest request = new MockHttpServletRequest();
//				MockHttpServletResponse response = new MockHttpServletResponse();
//				request.setContextPath(contextPath);
////				templateEngine.process("/admin/common/home", new WebContext(request, response, request.getServletContext()));
//				
//				try {
//					viewResolver.resolveViewName("/admin/common/home", Locale.US)
//						.render(new HashMap<String, String>(), request, response);
//					System.out.println(response.getContentAsString());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//		t.start();
		
		
		return "/admin/common/home";
	}
	
}
