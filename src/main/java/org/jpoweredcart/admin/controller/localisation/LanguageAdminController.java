package org.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.entity.localisation.Language;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import org.jpoweredcart.common.security.UserPermissions;
import org.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LanguageAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@RequestMapping(value="/admin/localisation/language")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Language> languageList = languageAdminModel.getLanguages(pageParam);
		model.addAttribute("languages", languageList);
		
		int total = languageAdminModel.getTotalLanguages();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/language"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/languageList";
	}
	
	@RequestMapping(value="/admin/localisation/language/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("language", new Language());
		
		return "/admin/localisation/languageForm";
	}
	
	@RequestMapping(value="/admin/localisation/language/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		Language language = languageAdminModel.getLanguage(id);
		model.addAttribute("language", language);
		
		return "/admin/localisation/languageForm";
	}
	
	@RequestMapping(value="/admin/localisation/language/save", method=RequestMethod.POST)
	public String save(@Valid Language language, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("language", language);
			return "/admin/localisation/languageForm";
		}
		
		languageAdminModel.saveLanguage(language);
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/language";
	}
	
	@RequestMapping(value="/admin/localisation/language/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				languageAdminModel.deleteLanguage(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/language";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/language", 
				new UnauthorizedAdminException());
	}
	
}
