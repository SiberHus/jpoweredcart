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

import com.jpoweredcart.admin.bean.catalog.CategoryForm;
import com.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import com.jpoweredcart.admin.model.design.LayoutAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.service.ImageService;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/catalog/category")
public class CategoryAdminController extends BaseController {
	
	@Inject
	private CategoryAdminModel categoryAdminModel;
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private LayoutAdminModel layoutAdminModel; 
	
	@Inject
	private ImageService imageService;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		PageParam pageParam = createPageParam(request);
		String separator = message(request, "text.separator");
		List<Category> infoList = categoryAdminModel.getList(separator);
		model.addAttribute("categories", infoList);
		
		int total = categoryAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/catalog/category"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/catalog/categoryList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		model.addAttribute("categoryForm", categoryAdminModel.newForm());
		addFormAttributes(model, request);
		
		return "/admin/catalog/categoryForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
		
		checkModifyPermission();
		CategoryForm infoForm = categoryAdminModel.getForm(id);
		model.addAttribute("categoryForm", infoForm);
		
		addFormAttributes(model, request);
		
		return "/admin/catalog/categoryForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid CategoryForm infoForm, BindingResult result, Model model, 
			RedirectAttributes redirect, HttpServletRequest request){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("categoryForm", infoForm);
			model.addAttribute("msg_warning", "error.warning");
			addFormAttributes(model, request);
			return "/admin/catalog/categoryForm";
		}
		
		
		if(infoForm.getId()!=null){
			categoryAdminModel.update(infoForm);
		}else{
			categoryAdminModel.create(infoForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/catalog/category";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				categoryAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/catalog/category";
	}
	
	private void addFormAttributes(Model model, HttpServletRequest request){
		String noImageUrl = imageService.getImageUrl("no_image.jpg");
		model.addAttribute("noImage", noImageUrl);
		String separator = message(request, "text.separator");
		model.addAttribute("categories", categoryAdminModel.getList(separator));
		model.addAttribute("stores", storeAdminModel.getAll());
		model.addAttribute("layouts", layoutAdminModel.getList(PageParam.list()));
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("catalog/category", 
				new UnauthorizedAdminException());
	}
	
}
