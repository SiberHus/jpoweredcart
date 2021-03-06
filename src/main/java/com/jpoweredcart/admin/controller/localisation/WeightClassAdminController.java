package com.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jpoweredcart.admin.model.catalog.ProductAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.localisation.WeightClassAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.localisation.WeightClass;
import com.jpoweredcart.common.entity.localisation.WeightClassDesc;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.system.setting.SettingKey;
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
		List<WeightClass> weightClassList = weightClassAdminModel.getList(pageParam);
		model.addAttribute("weightClasses", weightClassList);
		
		int total = weightClassAdminModel.getTotal();
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
		List<Language> languages = languageAdminModel.getList(new PageParam());
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
		
		WeightClass weightClass = weightClassAdminModel.get(id);
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
			weightClassAdminModel.update(weightClass);
		}else{
			weightClassAdminModel.create(weightClass);
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
				if(productAdminModel.getTotalByWeightClassId(id)>0){
					redirect.addFlashAttribute("msg_warning", "error.product");
					error = true; break;
				}
			}
			if(!error) for(Integer id: ids){
				weightClassAdminModel.delete(id);
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
