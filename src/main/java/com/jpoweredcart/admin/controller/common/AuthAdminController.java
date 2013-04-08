package com.jpoweredcart.admin.controller.common;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.bean.common.PasswordResetForm;
import com.jpoweredcart.admin.model.user.UserAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.service.email.EmailMessage;
import com.jpoweredcart.common.service.email.EmailService;
import com.jpoweredcart.common.service.setting.SettingKey;
import com.jpoweredcart.common.service.template.TemplateService;

@Controller
@RequestMapping("/admin/common")
public class AuthAdminController extends BaseController {

	@Inject
	private UserAdminModel userAdminModel;
	
	@Inject
	private TemplateService templateService;
	
	@Inject 
	private EmailService emailService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model) {
		
		return "/admin/common/login";
	}
	
	@RequestMapping(value="/forgotten", method=RequestMethod.GET)
	public String forgotPassword(){
		
		return "/admin/common/forgotten";
	}
	
	@RequestMapping(value="/forgotten", method=RequestMethod.POST)
	public String requestNewPassword(@RequestParam(value="email", required=false) 
		String email, Model model, HttpServletRequest request, RedirectAttributes redirect){
		
		if(StringUtils.isBlank(email) || 
				userAdminModel.getTotalByEmail(email)==0) {
			model.addAttribute("msg_warning", "error.email");
			return "/admin/common/forgotten";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		Environment env = getSettingService().getEnvironment();
		String serverUrl = request.isSecure()?env.getProperty("app.http")
				:env.getProperty("app.https");
		String code = userAdminModel.generateCode(email);
		params.put("serverUrl", serverUrl);
		params.put("url", serverUrl+"/admin/common/reset?code="+code);
		params.put("email", email);
		params.put("ipAddress", request.getRemoteAddr());
		
		String template = templateService.renderTemplate("/admin/email/passwordReset", params);
		
		EmailMessage message = new EmailMessage();
		message.setTo(email);
		message.setBodyHtml(template);
		message.setSubject(message(request, "text.subject"));
		emailService.send(message);
		
		redirect.addFlashAttribute("msg_success", message(request, "text.success"));
		
		return "redirect:/admin/common/login";
	}
	
	@RequestMapping(value="/reset", method=RequestMethod.GET)
	public String showResetPassword(@RequestParam(value="code", required=true) 
		String code, Model model, HttpServletRequest request){
		
		model.addAttribute("code", code);
		model.addAttribute("passwordResetForm", new PasswordResetForm());
		
		return "/admin/common/reset";
	}
	
	@RequestMapping(value="/reset", method=RequestMethod.POST)
	public String updatePassword(@Valid PasswordResetForm resetForm, BindingResult result,
			Model model, HttpServletRequest request, RedirectAttributes redirect){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth!=null && auth.isAuthenticated() && 
				!"anonymousUser".equals(auth.getPrincipal().toString())){
			return "redirect:/admin/common/home";
		}
		
		if(getSettingService().getConfig(SettingKey.CFG_PASSWORD, Integer.class)==0){
			return "redirect:/admin/common/login";
		}
		
		if(result.hasErrors() || !resetForm.getPassword().equals(resetForm.getConfirm())){
			model.addAttribute("passwordResetForm", resetForm);
			model.addAttribute("error_confirm", "error");
			return "/admin/common/reset";
		}
		
		User user = userAdminModel.getUserByCode(resetForm.getCode());
		if(user!=null){
			
			userAdminModel.updatePassword(user.getId(), resetForm.getPassword());
			
			redirect.addFlashAttribute("msg_success", message(request, "text.success"));
			
		}else{
			getSettingService().setConfig(SettingKey.CFG_PASSWORD, "1");
		}
			
		return "redirect:/admin/common/login";
	}
	
}
