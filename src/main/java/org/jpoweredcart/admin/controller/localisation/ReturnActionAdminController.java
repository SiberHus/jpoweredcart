package org.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jpoweredcart.admin.entity.localisation.Language;
import org.jpoweredcart.admin.entity.localisation.ReturnActions;
import org.jpoweredcart.admin.entity.localisation.ReturnActions.ReturnAction;
import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.localisation.ReturnActionAdminModel;
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
public class ReturnActionAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private ReturnActionAdminModel returnActionAdminModel;
	
	@RequestMapping(value="/admin/localisation/returnAction")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<ReturnAction> returnActions = returnActionAdminModel.getReturnActions(pageParam);
		model.addAttribute("returnActions", returnActions);
		
		Integer defaultReturnActionId = getSettingService().getConfig(SettingKey.CFG_RETURN_STATUS_ID, Integer.class);
		model.addAttribute("defaultReturnActionId", defaultReturnActionId);
		
		int total = returnActionAdminModel.getTotalReturnActions();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/returnAction"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/returnActionList";
	}
	
	@RequestMapping(value="/admin/localisation/returnAction/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		List<Language> languages = languageAdminModel.getLanguages(new PageParam());
		List<ReturnAction> list = new ArrayList<ReturnAction>();
		for(Language language: languages){
			ReturnAction returnAction = new ReturnAction();
			returnAction.setLanguageId(language.getId());
			returnAction.setLanguageName(language.getName());
			returnAction.setLanguageImage(language.getImage());
			list.add(returnAction);
		}
		ReturnActions returnActions = new ReturnActions(list);
		returnActions.setNewEntities(true);
		model.addAttribute("returnActions", returnActions);
		
		return "/admin/localisation/returnActionForm";
	}
	
	@RequestMapping(value="/admin/localisation/returnAction/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		ReturnActions returnActions = returnActionAdminModel.getReturnActions(id);
		returnActions.setNewEntities(false);
		model.addAttribute("returnActions", returnActions);
		
		return "/admin/localisation/returnActionForm";
	}
	
	@RequestMapping(value="/admin/localisation/returnAction/save", method=RequestMethod.POST)
	public String save(ReturnActions returnActions, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(returnActions.hasErrors()){
			model.addAttribute("returnActions", returnActions);
			return "/admin/localisation/returnActionForm";
		}
		
		ReturnAction returnActionList[] = returnActions.getList().toArray(new ReturnAction[0]);
		if(returnActions.isNewEntities()){
			returnActionAdminModel.addReturnAction(returnActionList);
		}else{
			returnActionAdminModel.updateReturnAction(returnActionList);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/returnAction";
	}
	
	@RequestMapping(value="/admin/localisation/returnAction/delete", method=RequestMethod.POST)
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
				returnActionAdminModel.deleteReturnAction(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/returnAction";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/return_status", 
				new UnauthorizedAdminException());
	}
	
}