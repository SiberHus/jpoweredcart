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

import com.jpoweredcart.admin.form.sale.CustomerGroupForm;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.system.setting.SettingKey;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/sale/customerGroup")
public class CustomerGroupAdminController extends BaseController {

	@Inject
	private CustomerGroupAdminModel customerGroupAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		Integer defaultCustomerGroupId = getSettingService().getConfig(
				SettingKey.CFG_CUSTOMER_GROUP_ID, Integer.class);
		model.addAttribute("defaultCustomerGroupId", defaultCustomerGroupId);
		PageParam pageParam = createPageParam(request);
		List<CustomerGroup> customerGroupList = customerGroupAdminModel.getList(pageParam);
		model.addAttribute("customerGroups", customerGroupList);
		int total = customerGroupAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/sale/customerGroup"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/sale/customerGroupList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		addFormAttributes(customerGroupAdminModel.newForm(), model);
		
		return "/admin/sale/customerGroupForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		CustomerGroupForm cgForm = customerGroupAdminModel.getForm(id);
		addFormAttributes(cgForm, model);
		
		return "/admin/sale/customerGroupForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid CustomerGroupForm cgForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			addFormAttributes(cgForm, model);
			return "/admin/sale/customerGroupForm";
		}
		
		if(cgForm.getId()!=null){
			customerGroupAdminModel.update(cgForm);
		}else{
			customerGroupAdminModel.create(cgForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/sale/customerGroup";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				customerGroupAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/sale/customerGroup";
	}
	
	private void addFormAttributes(CustomerGroupForm form, Model model){
		
		model.addAttribute("customerGroupForm", form);
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("sale/customer_group", 
				new UnauthorizedAdminException());
	}
}
