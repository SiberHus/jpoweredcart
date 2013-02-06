package org.jpoweredcart.admin.controller.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.form.catalog.InformationForm;
import org.jpoweredcart.admin.model.catalog.InformationAdminModel;
import org.jpoweredcart.admin.model.design.LayoutAdminModel;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToStore;
import org.jpoweredcart.common.entity.localisation.Language;
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
@RequestMapping("/admin/catalog/information")
public class InformationAdminController extends BaseController {
	
	@Inject
	private InformationAdminModel informationAdminModel;
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private LayoutAdminModel layoutAdminModel; 
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		PageParam pageParam = createPageParam(request);
		List<Information> infoList = informationAdminModel.getInformations(pageParam);
		model.addAttribute("informations", infoList);
		
		int total = informationAdminModel.getTotalInformations();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/catalog/information"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/catalog/informationList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		InformationForm informationForm = new InformationForm();
		List<InformationDesc> descs = new ArrayList<InformationDesc>();
		for(Language language: languageAdminModel.getLanguages(PageParam.list())){
			InformationDesc desc = new InformationDesc();
			desc.setLanguageId(language.getId());
			desc.setLanguageName(language.getName());
			desc.setLanguageImage(language.getImage());
			descs.add(desc);
		}
		informationForm.setDescs(descs);
		model.addAttribute("informationForm", informationForm);
		addFormAttributes(model);
		
		return "/admin/catalog/informationForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		InformationForm informationForm = new InformationForm();
		Information information = informationAdminModel.getInformation(id);
		informationForm.setData(information);
		informationForm.setDescs(informationAdminModel.getInformationDescriptions(information.getId()));
		
		informationForm.setStores(informationAdminModel.getInformationStores(information.getId()));
		informationForm.setLayouts(informationAdminModel.getInformationLayouts(information.getId()));
		
		model.addAttribute("informationForm", informationForm);
		
		addFormAttributes(model);
		
		return "/admin/catalog/informationForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid InformationForm informationForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		for(InformationToStore its : informationForm.getStores()){
			logger.info(its.getInformationId()+"=>"+its.getStoreId());
		}
		
		if(result.hasErrors()){
			model.addAttribute("informationForm", informationForm);
			model.addAttribute("msg_warning", "error.warning");
			addFormAttributes(model);
			return "/admin/catalog/informationForm";
		}
		
		
		if(informationForm.getData().getId()!=null){
			informationAdminModel.updateInformation(informationForm);
		}else{
			informationAdminModel.addInformation(informationForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/catalog/information";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				informationAdminModel.deleteInformation(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/catalog/information";
	}
	
	private void addFormAttributes(Model model){
		model.addAttribute("stores", storeAdminModel.getAllStores());
		model.addAttribute("layouts", layoutAdminModel.getLayouts(PageParam.list()));
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("catalog/information", 
				new UnauthorizedAdminException());
	}
	
}
