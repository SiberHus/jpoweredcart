package com.jpoweredcart.admin.controller.sale;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.form.sale.AffiliateForm;
import com.jpoweredcart.admin.form.sale.CountryWithZones;
import com.jpoweredcart.admin.form.sale.filter.AffiliateFilter;
import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.admin.model.localisation.ZoneAdminModel;
import com.jpoweredcart.admin.model.sale.AffiliateAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Country;
import com.jpoweredcart.common.entity.localisation.Zone;
import com.jpoweredcart.common.entity.sale.Affiliate;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/sale/affiliate")
public class AffiliateAdminController extends BaseController {

	@Inject
	private CountryAdminModel countryAdminModel;
	
	@Inject
	private ZoneAdminModel zoneAdminModel;
	
	@Inject
	private AffiliateAdminModel affiliateAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(AffiliateFilter filter, Model model, HttpServletRequest request){
		
		addJsDateFormatAttribute(model, request);
		model.addAttribute("filter", filter);
		
		PageParam pageParam = createPageParam(request);
		List<Affiliate> affiliateList = affiliateAdminModel.getList(filter, pageParam);
		model.addAttribute("affiliates", affiliateList);
		int total = affiliateAdminModel.getTotal(filter);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/sale/affiliate"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/sale/affiliateList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		addFormAttributes(affiliateAdminModel.newForm(), model);
		
		return "/admin/sale/affiliateForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		AffiliateForm affForm = affiliateAdminModel.getForm(id);
		addFormAttributes(affForm, model);
		
		return "/admin/sale/affiliateForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid AffiliateForm affForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			addFormAttributes(affForm, model);
			return "/admin/sale/affiliateForm";
		}
		
		if(affForm.getId()!=null){
			affiliateAdminModel.update(affForm);
		}else{
			affiliateAdminModel.create(affForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/sale/affiliate";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				affiliateAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/sale/affiliate";
	}
	
	@RequestMapping(value="/country.json", method=RequestMethod.GET)
	public @ResponseBody CountryWithZones getCountriesAsJson(@RequestParam("countryId") Integer countryId, 
			HttpServletRequest request){
		if(countryId==null || countryId==0) return new CountryWithZones();
		CountryWithZones cwz = (CountryWithZones)countryAdminModel
				.get(countryId, CountryWithZones.class);
		if(cwz!=null){
			List<Zone> zones = zoneAdminModel.getAllByCountryId(countryId);
			cwz.setZones(zones);
		}
		return cwz;
	}
	
	@InitBinder
	@Override
	protected void initBinder(WebDataBinder binder, HttpServletRequest request) {
		super.initBinder(binder, request);
	}

	private void addFormAttributes(AffiliateForm form, Model model){
		
		model.addAttribute("affiliateForm", form);
		model.addAttribute("countries", countryAdminModel.getAll());
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("sale/affiliate", 
				new UnauthorizedAdminException());
	}
}
