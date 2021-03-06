package com.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.localisation.StockStatusAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.localisation.StockStatuses;
import com.jpoweredcart.common.entity.localisation.StockStatuses.StockStatus;
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
public class StockStatusAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private StockStatusAdminModel stockStatusAdminModel;
	
	@RequestMapping(value="/admin/localisation/stockStatus")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<StockStatus> stockStatuses = stockStatusAdminModel.getStockStatuses(pageParam);
		model.addAttribute("stockStatuses", stockStatuses);
		
		Integer defaultStockStatusId = getSettingService().getConfig(SettingKey.CFG_STOCK_STATUS_ID, Integer.class);
		model.addAttribute("defaultStockStatusId", defaultStockStatusId);
		
		int total = stockStatusAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/stockStatus"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/stockStatusList";
	}
	
	@RequestMapping(value="/admin/localisation/stockStatus/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		List<Language> languages = languageAdminModel.getList(new PageParam());
		List<StockStatus> list = new ArrayList<StockStatus>();
		for(Language language: languages){
			StockStatus stockStatus = new StockStatus();
			stockStatus.setLanguageId(language.getId());
			stockStatus.setLanguageName(language.getName());
			stockStatus.setLanguageImage(language.getImage());
			list.add(stockStatus);
		}
		StockStatuses stockStatuses = new StockStatuses(list);
		stockStatuses.setNewEntities(true);
		model.addAttribute("stockStatuses", stockStatuses);
		
		return "/admin/localisation/stockStatusForm";
	}
	
	@RequestMapping(value="/admin/localisation/stockStatus/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		StockStatuses stockStatuses = stockStatusAdminModel.getStockStatuses(id);
		stockStatuses.setNewEntities(false);
		model.addAttribute("stockStatuses", stockStatuses);
		
		return "/admin/localisation/stockStatusForm";
	}
	
	@RequestMapping(value="/admin/localisation/stockStatus/save", method=RequestMethod.POST)
	public String save(StockStatuses stockStatuses, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(stockStatuses.hasErrors()){
			model.addAttribute("stockStatuses", stockStatuses);
			return "/admin/localisation/stockStatusForm";
		}
		
		StockStatus stockStatusList[] = stockStatuses.getList().toArray(new StockStatus[0]);
		if(stockStatuses.isNewEntities()){
			stockStatusAdminModel.create(stockStatusList);
		}else{
			stockStatusAdminModel.update(stockStatusList);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/stockStatus";
	}
	
	@RequestMapping(value="/admin/localisation/stockStatus/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			for(Integer id: ids){
				if(getSettingService().getConfig(SettingKey.CFG_STOCK_STATUS_ID, 
						Integer.class) == id){
					redirect.addFlashAttribute("msg_warning", "error.default");
					error=true; break;
				}
				
			}
			if(!error) for(Integer id: ids){
				stockStatusAdminModel.delete(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/stockStatus";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/stock_status", 
				new UnauthorizedAdminException());
	}
	
}