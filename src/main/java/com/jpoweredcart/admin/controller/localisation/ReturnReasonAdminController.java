package com.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.localisation.ReturnReasonAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.localisation.ReturnReasons;
import com.jpoweredcart.common.entity.localisation.ReturnReasons.ReturnReason;
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
public class ReturnReasonAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private ReturnReasonAdminModel returnReasonAdminModel;
	
	@RequestMapping(value="/admin/localisation/returnReason")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<ReturnReason> returnReasons = returnReasonAdminModel.getList(pageParam);
		model.addAttribute("returnReasons", returnReasons);
		
		Integer defaultReturnReasonId = getSettingService().getConfig(SettingKey.CFG_RETURN_STATUS_ID, Integer.class);
		model.addAttribute("defaultReturnReasonId", defaultReturnReasonId);
		
		int total = returnReasonAdminModel.getTotal();
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
		
		List<Language> languages = languageAdminModel.getList(new PageParam());
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
			returnReasonAdminModel.create(returnReasonList);
		}else{
			returnReasonAdminModel.update(returnReasonList);
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
				returnReasonAdminModel.delete(id);
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