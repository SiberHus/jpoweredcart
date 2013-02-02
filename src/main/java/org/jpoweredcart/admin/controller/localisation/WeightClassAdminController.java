package org.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.entity.localisation.Language;
import org.jpoweredcart.admin.entity.localisation.WeightClass;
import org.jpoweredcart.admin.entity.localisation.WeightClassDesc;
import org.jpoweredcart.admin.model.catalog.ProductAdminModel;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.localisation.WeightClassAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
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
public class WeightClassAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private ProductAdminModel productAdminModel;
	
	@Inject
	private WeightClassAdminModel weightClassAdminModel;
	
	@RequestMapping(value="/admin/localisation/weightClass")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<WeightClass> weightClassList = weightClassAdminModel.getWeightClasses(pageParam);
		model.addAttribute("weightClasses", weightClassList);
		
		int total = weightClassAdminModel.getTotalWeightClasses();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/weightClass"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/weightClassList";
	}
	
	@RequestMapping(value="/admin/localisation/weightClass/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		WeightClass weightClass = new WeightClass();
		List<Language> languages = languageAdminModel.getLanguages(new PageParam());
		List<WeightClassDesc> descs = new ArrayList<WeightClassDesc>();
		for(Language language: languages){
			WeightClassDesc desc = new WeightClassDesc();
			desc.setLanguageId(language.getId());
			desc.setLanguageName(language.getName());
			desc.setLanguageImage(language.getImage());
			descs.add(desc);
		}
		weightClass.setDescs(descs);
		model.addAttribute("weightClass", weightClass);
		
		return "/admin/localisation/weightClassForm";
	}
	
	@RequestMapping(value="/admin/localisation/weightClass/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		WeightClass weightClass = weightClassAdminModel.getWeightClass(id);
		model.addAttribute("weightClass", weightClass);
		return "/admin/localisation/weightClassForm";
	}
	
	@RequestMapping(value="/admin/localisation/weightClass/save", method=RequestMethod.POST)
	public String save(@Valid WeightClass weightClass, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("weightClass", weightClass);
			return "/admin/localisation/weightClassForm";
		}
		if(weightClass.getId()!=null){
			weightClassAdminModel.updateWeightClass(weightClass);
		}else{
			weightClassAdminModel.addWeightClass(weightClass);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/weightClass";
	}
	
	@RequestMapping(value="/admin/localisation/weightClass/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			for(Integer id: ids){
				if(id.equals(getSettingService().getConfig(SettingKey.CFG_WEIGHT_CLASS_ID, Integer.class))){
					redirect.addFlashAttribute("msg_warning", "error.default");
					error = true; break;
				}
				if(productAdminModel.getTotalProductsByWeightClassId(id)>0){
					redirect.addFlashAttribute("msg_warning", "error.product");
					error = true; break;
				}
			}
			if(!error) for(Integer id: ids){
				weightClassAdminModel.deleteWeightClass(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/weightClass";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/weight_class", 
				new UnauthorizedAdminException());
	}
	
}
