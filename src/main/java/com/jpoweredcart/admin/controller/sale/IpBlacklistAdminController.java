package com.jpoweredcart.admin.controller.sale;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.form.sale.IpBlacklistForm;
import com.jpoweredcart.admin.model.sale.IpBlacklistAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.IpBlacklist;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping("/admin/sale/ipBlacklist")
public class IpBlacklistAdminController extends BaseController {
	
	@Inject
	private IpBlacklistAdminModel ipBlacklistAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<IpBlacklist> ipBlacklistList = ipBlacklistAdminModel.getList(pageParam);
		model.addAttribute("ipBlacklists", ipBlacklistList);
		int total = ipBlacklistAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/sale/ipBlacklist"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/sale/ipBlacklistList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		addFormAttributes(ipBlacklistAdminModel.newForm(), model);
		
		return "/admin/sale/ipBlacklistForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		IpBlacklistForm ipBlacklistForm = ipBlacklistAdminModel.getForm(id);
		addFormAttributes(ipBlacklistForm, model);
		
		return "/admin/sale/ipBlacklistForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid IpBlacklistForm ipBlacklistForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			addFormAttributes(ipBlacklistForm, model);
			return "/admin/sale/ipBlacklistForm";
		}
		
		if(ipBlacklistForm.getId()!=null){
			ipBlacklistAdminModel.update(ipBlacklistForm);
		}else{
			ipBlacklistAdminModel.create(ipBlacklistForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/sale/ipBlacklist";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				ipBlacklistAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/sale/ipBlacklist";
	}
	
	private void addFormAttributes(IpBlacklistForm form, Model model){
	
		model.addAttribute("ipBlacklistForm", form);
	}

	private void checkModifyPermission(){
		UserPermissions.checkModify("sale/customer_blacklist", 
				new UnauthorizedAdminException());
	}
	
}
