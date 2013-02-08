package com.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Country;
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
public class CountryAdminController extends BaseController {
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@RequestMapping(value="/admin/localisation/country")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Country> countryList = countryAdminModel.getList(pageParam);
		model.addAttribute("countries", countryList);
		
		int total = countryAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/country"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/countryList";
	}
	
	@RequestMapping(value="/admin/localisation/country/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("country", new Country());
		
		return "/admin/localisation/countryForm";
	}
	
	@RequestMapping(value="/admin/localisation/country/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		Country country = countryAdminModel.get(id);
		model.addAttribute("country", country);
		return "/admin/localisation/countryForm";
	}
	
	@RequestMapping(value="/admin/localisation/country/save", method=RequestMethod.POST)
	public String save(@Valid Country country, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("country", country);
			return "/admin/localisation/countryForm";
		}
		
		if(country.getId()!=null){
			countryAdminModel.update(country);
		}else{
			countryAdminModel.create(country);
		}
		
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/country";
	}
	
	@RequestMapping(value="/admin/localisation/country/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				countryAdminModel.delete(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/country";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/country", 
				new UnauthorizedAdminException());
	}
	
}
