package com.jpoweredcart.admin.controller.localisation;

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

import com.jpoweredcart.admin.bean.localisation.LengthClassForm;
import com.jpoweredcart.admin.model.catalog.ProductAdminModel;
import com.jpoweredcart.admin.model.localisation.LengthClassAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.service.SettingKey;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping(value="/admin/localisation/lengthClass")
public class LengthClassAdminController extends BaseController {
	
	@Inject
	private ProductAdminModel productAdminModel;
	
	@Inject
	private LengthClassAdminModel lengthClassAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<LengthClass> lengthClassList = lengthClassAdminModel.getList(pageParam);
		model.addAttribute("lengthClasses", lengthClassList);
		
		int total = lengthClassAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/lengthClass"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/lengthClassList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("lengthClassForm", lengthClassAdminModel.newForm());
		
		return "/admin/localisation/lengthClassForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		LengthClass lcForm = lengthClassAdminModel.getForm(id);
		model.addAttribute("lengthClassForm", lcForm);
		return "/admin/localisation/lengthClassForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid LengthClassForm lcForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("lengthClassForm", lcForm);
			return "/admin/localisation/lengthClassForm";
		}
		if(lcForm.getId()!=null){
			lengthClassAdminModel.update(lcForm);
		}else{
			lengthClassAdminModel.create(lcForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/lengthClass";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			for(Integer id: ids){
				if(id.equals(getSettingService().getConfig(SettingKey.CFG_LENGTH_CLASS_ID, Integer.class))){
					redirect.addFlashAttribute("msg_warning", "error.default");
					error = true; break;
				}
				if(productAdminModel.getTotalByLengthClassId(id)>0){
					redirect.addFlashAttribute("msg_warning", "error.product");
					error = true; break;
				}
			}
			if(!error) for(Integer id: ids){
				lengthClassAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/localisation/lengthClass";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/length_class", 
				new UnauthorizedAdminException());
	}
	
}
