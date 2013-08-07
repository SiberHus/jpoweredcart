package com.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnStatusAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.localisation.ReturnStatuses;
import com.jpoweredcart.common.entity.localisation.ReturnStatuses.ReturnStatus;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.system.setting.SettingKey;
import com.jpoweredcart.common.view.Pagination;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ReturnStatusAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private ReturnStatusAdminModel returnStatusAdminModel;
	
	@RequestMapping(value="/admin/localisation/returnStatus")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<ReturnStatus> returnStatuses = returnStatusAdminModel.getList(pageParam);
		model.addAttribute("returnStatuses", returnStatuses);
		
		Integer defaultReturnStatusId = getSettingService().getConfig(SettingKey.CFG_RETURN_STATUS_ID, Integer.class);
		model.addAttribute("defaultReturnStatusId", defaultReturnStatusId);
		
		int total = returnStatusAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/returnStatus"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/returnStatusList";
	}
	
	@RequestMapping(value="/admin/localisation/returnStatus/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		List<Language> languages = languageAdminModel.getList(new PageParam());
		List<ReturnStatus> list = new ArrayList<ReturnStatus>();
		for(Language language: languages){
			ReturnStatus returnStatus = new ReturnStatus();
			returnStatus.setLanguageId(language.getId());
			returnStatus.setLanguageName(language.getName());
			returnStatus.setLanguageImage(language.getImage());
			list.add(returnStatus);
		}
		ReturnStatuses returnStatuses = new ReturnStatuses(list);
		returnStatuses.setNewEntities(true);
		model.addAttribute("returnStatuses", returnStatuses);
		
		return "/admin/localisation/returnStatusForm";
	}
	
	@RequestMapping(value="/admin/localisation/returnStatus/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		ReturnStatuses returnStatuses = returnStatusAdminModel.getReturnStatuses(id);
		returnStatuses.setNewEntities(false);
		model.addAttribute("returnStatuses", returnStatuses);
		
		return "/admin/localisation/returnStatusForm";
	}
	
	@RequestMapping(value="/admin/localisation/returnStatus/save", method=RequestMethod.POST)
	public String save(ReturnStatuses returnStatuses, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(returnStatuses.hasErrors()){
			model.addAttribute("returnStatuses", returnStatuses);
			return "/admin/localisation/returnStatusForm";
		}
		
		ReturnStatus returnStatusList[] = returnStatuses.getList().toArray(new ReturnStatus[0]);
		if(returnStatuses.isNewEntities()){
			returnStatusAdminModel.create(returnStatusList);
		}else{
			returnStatusAdminModel.update(returnStatusList);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/returnStatus";
	}
	
	@RequestMapping(value="/admin/localisation/returnStatus/delete", method=RequestMethod.POST)
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
				returnStatusAdminModel.delete(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/returnStatus";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/return_status", 
				new UnauthorizedAdminException());
	}
	
}