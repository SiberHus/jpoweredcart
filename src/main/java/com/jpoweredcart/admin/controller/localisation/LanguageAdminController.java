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

import com.jpoweredcart.admin.bean.localisation.LanguageForm;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping(value="/admin/localisation/language")
public class LanguageAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Language> languageList = languageAdminModel.getList(pageParam);
		model.addAttribute("languages", languageList);
		
		int total = languageAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/language"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/languageList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("languageForm", languageAdminModel.newForm());
		
		return "/admin/localisation/languageForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		LanguageForm languageForm = languageAdminModel.getForm(id);
		model.addAttribute("languageForm", languageForm);
		
		return "/admin/localisation/languageForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid LanguageForm languageForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("languageForm", languageForm);
			return "/admin/localisation/languageForm";
		}
		
		if(languageForm.getId()!=null){
			languageAdminModel.update(languageForm);
		}else{
			languageAdminModel.create(languageForm);
		}
		
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/language";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				languageAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/localisation/language";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/language", 
				new UnauthorizedAdminException());
	}
	
}
