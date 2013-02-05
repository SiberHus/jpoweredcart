package org.jpoweredcart.admin.controller.localisation;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.Currency;
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
public class CurrencyAdminController extends BaseController {
	
	@Inject
	private CurrencyAdminModel currencyAdminModel;
	
	@RequestMapping(value="/admin/localisation/currency")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Currency> currencyList = currencyAdminModel.getCurrencies(pageParam);
		model.addAttribute("currencies", currencyList);
		
		int total = currencyAdminModel.getTotalCurrencies();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/currency"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/currencyList";
	}
	
	@RequestMapping(value="/admin/localisation/currency/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("currency", new Currency());
		
		return "/admin/localisation/currencyForm";
	}
	
	@RequestMapping(value="/admin/localisation/currency/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		Currency currency = currencyAdminModel.getCurrency(id);
		model.addAttribute("currency", currency);
		return "/admin/localisation/currencyForm";
	}
	
	@RequestMapping(value="/admin/localisation/currency/save", method=RequestMethod.POST)
	public String save(@Valid Currency currency, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("currency", currency);
			return "/admin/localisation/currencyForm";
		}
		
		currencyAdminModel.saveCurrency(currency);
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/currency";
	}
	
	@RequestMapping(value="/admin/localisation/currency/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				currencyAdminModel.deleteCurrency(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/currency";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/currency", 
				new UnauthorizedAdminException());
	}
	
}
