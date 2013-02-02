package org.jpoweredcart.admin.controller.setting;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.entity.localisation.Country;
import org.jpoweredcart.admin.entity.setting.Store;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.DefaultSettings;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import org.jpoweredcart.common.security.UserPermissions;
import org.jpoweredcart.common.service.SettingGroup;
import org.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/setting/store")
public class StoreAdminController extends BaseController {
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Store> storeList = storeAdminModel.getAllStores();
		model.addAttribute("stores", storeList);
		int total = storeAdminModel.getTotalStores();
		System.out.println(total);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/setting/store"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/setting/storeList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		Map<String, Object> config = getSettingService().getAll(DefaultSettings.STORE_ID, SettingGroup.CONFIG);
		model.addAttribute("setting", config);
		
		Set<String> errors = new HashSet<String>();
		model.addAttribute("errors", errors);
		
		return "/admin/setting/storeForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid Country country, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("country", country);
			return "/admin/setting/storeForm";
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/setting/store";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/setting/store";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("setting/store", 
				new UnauthorizedAdminException());
	}
}
