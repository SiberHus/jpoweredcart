package com.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.bean.localisation.CountryForm;
import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Country;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping("/admin/localisation/country")
public class CountryAdminController extends BaseController {
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@RequestMapping(value={"", "/"})
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
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("countryForm", countryAdminModel.newForm());
		
		return "/admin/localisation/countryForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		CountryForm countryForm = countryAdminModel.getForm(id);
		model.addAttribute("countryForm", countryForm);
		return "/admin/localisation/countryForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid CountryForm countryForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("countryForm", countryForm);
			return "/admin/localisation/countryForm";
		}
		
		if(countryForm.getId()!=null){
			countryAdminModel.update(countryForm);
		}else{
			countryAdminModel.create(countryForm);
		}
		
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/country";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				countryAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/localisation/country";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/country", 
				new UnauthorizedAdminException());
	}
	
}
