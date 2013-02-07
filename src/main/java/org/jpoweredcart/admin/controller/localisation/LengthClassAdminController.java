package org.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.model.catalog.ProductAdminModel;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.localisation.LengthClassAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.Language;
import org.jpoweredcart.common.entity.localisation.LengthClass;
import org.jpoweredcart.common.entity.localisation.LengthClassDesc;
import org.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import org.jpoweredcart.common.security.UserPermissions;
import org.jpoweredcart.common.service.SettingKey;
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
public class LengthClassAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private ProductAdminModel productAdminModel;
	
	@Inject
	private LengthClassAdminModel lengthClassAdminModel;
	
	@RequestMapping(value="/admin/localisation/lengthClass")
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
	
	@RequestMapping(value="/admin/localisation/lengthClass/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		LengthClass lengthClass = new LengthClass();
		List<Language> languages = languageAdminModel.getList(new PageParam());
		List<LengthClassDesc> descs = new ArrayList<LengthClassDesc>();
		for(Language language: languages){
			LengthClassDesc desc = new LengthClassDesc();
			desc.setLanguageId(language.getId());
			desc.setLanguageName(language.getName());
			desc.setLanguageImage(language.getImage());
			descs.add(desc);
		}
		lengthClass.setDescs(descs);
		model.addAttribute("lengthClass", lengthClass);
		
		return "/admin/localisation/lengthClassForm";
	}
	
	@RequestMapping(value="/admin/localisation/lengthClass/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		LengthClass lengthClass = lengthClassAdminModel.get(id);
		model.addAttribute("lengthClass", lengthClass);
		return "/admin/localisation/lengthClassForm";
	}
	
	@RequestMapping(value="/admin/localisation/lengthClass/save", method=RequestMethod.POST)
	public String save(@Valid LengthClass lengthClass, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("lengthClass", lengthClass);
			return "/admin/localisation/lengthClassForm";
		}
		if(lengthClass.getId()!=null){
			lengthClassAdminModel.update(lengthClass);
		}else{
			lengthClassAdminModel.create(lengthClass);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/lengthClass";
	}
	
	@RequestMapping(value="/admin/localisation/lengthClass/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
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
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/lengthClass";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/length_class", 
				new UnauthorizedAdminException());
	}
	
}
