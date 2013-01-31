package org.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.entity.localisation.Country;
import org.jpoweredcart.admin.entity.localisation.Zone;
import org.jpoweredcart.admin.model.localisation.CountryAdminModel;
import org.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
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
public class ZoneAdminController extends BaseController {
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@Inject
	private ZoneAdminModel zoneAdminModel;
	
	@RequestMapping(value="/admin/localisation/zone")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Zone> zoneList = zoneAdminModel.getZones(pageParam);
		model.addAttribute("zones", zoneList);
		int total = zoneAdminModel.getTotalZones();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/zone"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/localisation/zoneList";
	}
	
	@RequestMapping(value="/admin/localisation/zone/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("zone", new Zone());
		addFormAttributes(model);
		
		return "/admin/localisation/zoneForm";
	}
	
	@RequestMapping(value="/admin/localisation/zone/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		Zone zone = zoneAdminModel.getZone(id);
		model.addAttribute("zone", zone);
		addFormAttributes(model);
		
		return "/admin/localisation/zoneForm";
	}
	
	@RequestMapping(value="/admin/localisation/zone/save", method=RequestMethod.POST)
	public String save(@Valid Zone zone, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("zone", zone);
			addFormAttributes(model);
			return "/admin/localisation/zoneForm";
		}
		
		zoneAdminModel.saveZone(zone);
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/zone";
	}
	
	@RequestMapping(value="/admin/localisation/zone/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				zoneAdminModel.deleteZone(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/zone";
	}
	
	private void addFormAttributes(Model model){
		List<Country> countries = countryAdminModel.getAllCountries();
		model.addAttribute("countries", countries);
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/zone", 
				new UnauthorizedAdminException());
	}
	
}
