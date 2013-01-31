package org.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.entity.localisation.TaxClass;
import org.jpoweredcart.admin.entity.localisation.TaxRate;
import org.jpoweredcart.admin.entity.localisation.TaxRule;
import org.jpoweredcart.admin.model.localisation.TaxClassAdminModel;
import org.jpoweredcart.admin.model.localisation.TaxRateAdminModel;
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
public class TaxClassAdminController extends BaseController {
	
	@Inject
	private TaxRateAdminModel taxRateAdminModel;
	
	@Inject
	private TaxClassAdminModel taxClassAdminModel;
	
	@RequestMapping(value="/admin/localisation/taxClass")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<TaxClass> currencies = taxClassAdminModel.getTaxClasses(pageParam);
		model.addAttribute("taxClasses", currencies);
		int total = taxClassAdminModel.getTotalTaxClasses();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/taxClass"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/taxClassList";
	}
	
	@RequestMapping(value="/admin/localisation/taxClass/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		model.addAttribute("taxClass", new TaxClass());
		addFormAttributes(model);
		
		return "/admin/localisation/taxClassForm";
	}
	
	@RequestMapping(value="/admin/localisation/taxClass/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		TaxClass taxClass = taxClassAdminModel.getTaxClass(id);
		model.addAttribute("taxClass", taxClass);
		addFormAttributes(model);
		
		return "/admin/localisation/taxClassForm";
	}
	
	@RequestMapping(value="/admin/localisation/taxClass/save", method=RequestMethod.POST)
	public String save(@Valid TaxClass taxClass, BindingResult result, Model model, 
			RedirectAttributes redirect, HttpServletRequest request){
		
		checkModifyPermission();
		
		String taxRateIds[] = request.getParameterValues("taxRules.taxRateId");
		String baseds[] = request.getParameterValues("taxRules.based");
		String priorities[] = request.getParameterValues("taxRules.priority");
		if(taxRateIds!=null){
			//i=1 because the first one is dummy value from prototype
			for(int i=1;i<taxRateIds.length;i++){
				int taxRateId = getConversionService().convert(taxRateIds[i], Integer.class);
				String based = baseds[i];
				int priority = getConversionService().convert(priorities[i], Integer.class);
				TaxRule taxRule = new TaxRule();
				taxRule.setTaxRateId(taxRateId);
				taxRule.setBased(based);
				taxRule.setPriority(priority);
				taxClass.getTaxRules().add(taxRule);
			}
		}
		
		if(result.hasErrors()){
			model.addAttribute("taxClass", taxClass);
			addFormAttributes(model);
			return "/admin/localisation/taxClassForm";
		}
		
		taxClassAdminModel.saveTaxClass(taxClass);
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/taxClass";
	}
	
	@RequestMapping(value="/admin/localisation/taxClass/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				taxClassAdminModel.deleteTaxClass(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/taxClass";
	}
	
	private void addFormAttributes(Model model){
		
		List<TaxRate> taxRates = taxRateAdminModel.getTaxRates(PageParam.list());
		model.addAttribute("taxRates", taxRates);
		
		
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/tax_class", 
				new UnauthorizedAdminException());
	}
	
}
