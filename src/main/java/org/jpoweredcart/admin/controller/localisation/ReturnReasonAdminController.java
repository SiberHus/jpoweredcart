package org.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jpoweredcart.admin.entity.localisation.Language;
import org.jpoweredcart.admin.entity.localisation.ReturnReasons;
import org.jpoweredcart.admin.entity.localisation.ReturnReasons.ReturnReason;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.localisation.ReturnReasonAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import org.jpoweredcart.common.security.UserPermissions;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReturnReasonAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private ReturnReasonAdminModel returnReasonAdminModel;
	
	@RequestMapping(value="/admin/localisation/returnReason")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<ReturnReason> returnReasons = returnReasonAdminModel.getReturnReasons(pageParam);
		model.addAttribute("returnReasons", returnReasons);
		
		Integer defaultReturnReasonId = getSettingService().getConfig(SettingKey.CFG_RETURN_STATUS_ID, Integer.class);
		model.addAttribute("defaultReturnReasonId", defaultReturnReasonId);
		
		int total = returnReasonAdminModel.getTotalReturnReasons();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/returnReason"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/returnReasonList";
	}
	
	@RequestMapping(value="/admin/localisation/returnReason/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		List<Language> languages = languageAdminModel.getLanguages(new PageParam());
		List<ReturnReason> list = new ArrayList<ReturnReason>();
		for(Language language: languages){
			ReturnReason returnReason = new ReturnReason();
			returnReason.setLanguageId(language.getId());
			returnReason.setLanguageName(language.getName());
			returnReason.setLanguageImage(language.getImage());
			list.add(returnReason);
		}
		ReturnReasons returnReasons = new ReturnReasons(list);
		returnReasons.setNewEntities(true);
		model.addAttribute("returnReasons", returnReasons);
		
		return "/admin/localisation/returnReasonForm";
	}
	
	@RequestMapping(value="/admin/localisation/returnReason/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		ReturnReasons returnReasons = returnReasonAdminModel.getReturnReasons(id);
		returnReasons.setNewEntities(false);
		model.addAttribute("returnReasons", returnReasons);
		
		return "/admin/localisation/returnReasonForm";
	}
	
	@RequestMapping(value="/admin/localisation/returnReason/save", method=RequestMethod.POST)
	public String save(ReturnReasons returnReasons, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(returnReasons.hasErrors()){
			model.addAttribute("returnReasons", returnReasons);
			return "/admin/localisation/returnReasonForm";
		}
		
		ReturnReason returnReasonList[] = returnReasons.getList().toArray(new ReturnReason[0]);
		if(returnReasons.isNewEntities()){
			returnReasonAdminModel.addReturnReason(returnReasonList);
		}else{
			returnReasonAdminModel.updateReturnReason(returnReasonList);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/returnReason";
	}
	
	@RequestMapping(value="/admin/localisation/returnReason/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			for(Integer id: ids){
				if(getSettingService().getConfig(SettingKey.CFG_RETURN_STATUS_ID, 
						Integer.class) == id){
					redirect.addFlashAttribute("msg_warning", "error.default");
					error=true; break;
				}
				
			}
			if(!error) for(Integer id: ids){
				returnReasonAdminModel.deleteReturnReason(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/returnReason";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/return_status", 
				new UnauthorizedAdminException());
	}
	
}