package com.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jpoweredcart.admin.model.localisation.GeoZoneAdminModel;
import com.jpoweredcart.admin.model.localisation.TaxRateAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.GeoZone;
import com.jpoweredcart.common.entity.localisation.TaxRate;
import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
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
public class TaxRateAdminController extends BaseController {
	
	@Inject
	private GeoZoneAdminModel geoZoneAdminModel;
	
	@Inject
	private CustomerGroupAdminModel customerGroupAdminModel;
	
	@Inject
	private TaxRateAdminModel taxRateAdminModel;
	
	@RequestMapping(value="/admin/localisation/taxRate")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<TaxRate> currencies = taxRateAdminModel.getList(pageParam);
		model.addAttribute("taxRates", currencies);
		int total = taxRateAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/taxRate"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/taxRateList";
	}
	
	@RequestMapping(value="/admin/localisation/taxRate/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		model.addAttribute("taxRate", new TaxRate());
		addFormAttributes(model);
		
		return "/admin/localisation/taxRateForm";
	}
	
	@RequestMapping(value="/admin/localisation/taxRate/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		TaxRate taxRate = taxRateAdminModel.get(id);
		model.addAttribute("taxRate", taxRate);
		addFormAttributes(model);
		
		return "/admin/localisation/taxRateForm";
	}
	
	@RequestMapping(value="/admin/localisation/taxRate/save", method=RequestMethod.POST)
	public String save(@Valid TaxRate taxRate, BindingResult result, Model model, 
			RedirectAttributes redirect, HttpServletRequest request){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("taxRate", taxRate);
			addFormAttributes(model);
			return "/admin/localisation/taxRateForm";
		}
		
		if(taxRate.getId()!=null){
			taxRateAdminModel.create(taxRate);
		}else{
			taxRateAdminModel.update(taxRate);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/taxRate";
	}
	
	@RequestMapping(value="/admin/localisation/taxRate/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				taxRateAdminModel.delete(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/taxRate";
	}
	
	private void addFormAttributes(Model model){
		
		List<GeoZone> geoZones = geoZoneAdminModel.getList(PageParam.list());
		model.addAttribute("geoZones", geoZones);
		
		List<CustomerGroup> customerGroups = customerGroupAdminModel.getList(PageParam.list());
		model.addAttribute("customerGroups", customerGroups);
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/tax_rate", 
				new UnauthorizedAdminException());
	}
	
}
