package com.jpoweredcart.admin.controller.sale;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jpoweredcart.admin.model.sale.IpBlacklistAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.IpBlacklist;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
		
		model.addAttribute("ipBlacklist", new IpBlacklist());
		
		return "/admin/sale/ipBlacklistForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		IpBlacklist ipBlacklist = ipBlacklistAdminModel.get(id);
		model.addAttribute("ipBlacklist", ipBlacklist);
		
		return "/admin/sale/ipBlacklistForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid IpBlacklist ipBlacklist, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("ipBlacklist", ipBlacklist);
			return "/admin/sale/ipBlacklistForm";
		}
		
		if(ipBlacklist.getId()!=null){
			ipBlacklistAdminModel.update(ipBlacklist);
		}else{
			ipBlacklistAdminModel.create(ipBlacklist);
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
	
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("sale/customer_blacklist", 
				new UnauthorizedAdminException());
	}
	
}
