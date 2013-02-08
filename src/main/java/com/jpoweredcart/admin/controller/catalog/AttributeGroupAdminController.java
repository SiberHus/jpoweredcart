package com.jpoweredcart.admin.controller.catalog;

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

import com.jpoweredcart.admin.bean.catalog.AttributeGroupForm;
import com.jpoweredcart.admin.model.catalog.AttributeGroupAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.AttributeGroup;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping("/admin/catalog/attributeGroup")
public class AttributeGroupAdminController extends BaseController {
	
	@Inject
	private AttributeGroupAdminModel attributeGroupAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<AttributeGroup> attributeGroupList = attributeGroupAdminModel.getList(pageParam);
		model.addAttribute("attributeGroups", attributeGroupList);
		
		int total = attributeGroupAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/catalog/attributeGroup"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/catalog/attributeGroupList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("attributeGroupForm", attributeGroupAdminModel.newForm());
		
		return "/admin/catalog/attributeGroupForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		AttributeGroup attrGrpForm = attributeGroupAdminModel.getForm(id);
		model.addAttribute("attributeGroupForm", attrGrpForm);
		return "/admin/catalog/attributeGroupForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid AttributeGroupForm attrGrpForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("attributeGroupForm", attrGrpForm);
			return "/admin/catalog/attributeGroupForm";
		}
		
		if(attrGrpForm.getId()!=null){
			attributeGroupAdminModel.update(attrGrpForm);
		}else{
			attributeGroupAdminModel.create(attrGrpForm);
		}
		
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/catalog/attributeGroup";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				attributeGroupAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/catalog/attributeGroup";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("catalog/attribute_group", 
				new UnauthorizedAdminException());
	}
	
}
