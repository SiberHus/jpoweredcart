package com.jpoweredcart.admin.controller.setting;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.model.design.LayoutAdminModel;
import com.jpoweredcart.admin.model.localisation.CountryAdminModel;
import com.jpoweredcart.admin.model.localisation.CurrencyAdminModel;
import com.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import com.jpoweredcart.admin.model.sale.CustomerGroupAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.design.Layout;
import com.jpoweredcart.common.entity.localisation.Country;
import com.jpoweredcart.common.entity.localisation.Currency;
import com.jpoweredcart.common.entity.localisation.Language;
import com.jpoweredcart.common.entity.sale.CustomerGroup;
import com.jpoweredcart.common.entity.setting.Store;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.service.media.MediaService;
import com.jpoweredcart.common.service.setting.DefaultSettings;
import com.jpoweredcart.common.service.setting.SettingGroup;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/setting/store")
public class StoreAdminController extends BaseController {
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private LayoutAdminModel layoutAdminModel;
	
	@Inject
	private CountryAdminModel countryAdminModel;
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private CurrencyAdminModel currencyAdminModel;
	
	@Inject
	private CustomerGroupAdminModel customerGroupAdminModel;
	
	@Inject
	private MediaService mediaService;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Store> storeList = storeAdminModel.getAll();
		model.addAttribute("stores", storeList);
		int total = storeAdminModel.getTotal();
		System.out.println(total);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/setting/store"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/setting/storeList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		Map<String, Object> config = getSettingService().getAll(DefaultSettings.STORE_ID, SettingGroup.CONFIG);
		model.addAttribute("setting", config);
		
		List<Layout> layoutList = layoutAdminModel.getList(PageParam.list());
		model.addAttribute("layouts", layoutList);
		
		List<Country> countryList = countryAdminModel.getAll();
		model.addAttribute("countries", countryList);
		
		List<Language> languageList = languageAdminModel.getList(PageParam.list());
		model.addAttribute("languages", languageList);
		
		List<Currency> currencyList = currencyAdminModel.getList(PageParam.list());
		model.addAttribute("currencies", currencyList);
		
		List<CustomerGroup> customerGroupList = customerGroupAdminModel.getList(PageParam.list());
		model.addAttribute("customerGroups", customerGroupList);
		
		
		
		String templateBaseDir = getSettingService().getEnvironment()
				.getProperty("template.baseDir")+"/catalog";
		templateBaseDir = request.getServletContext().getRealPath(templateBaseDir);
		String templates[] = new File(templateBaseDir).list();
		model.addAttribute("templates", templates);
		
		String noImageUrl = mediaService.getImageUrl("no_image.jpg");
		model.addAttribute("logo", noImageUrl);
		model.addAttribute("icon", noImageUrl);
		model.addAttribute("noImage", noImageUrl);
		
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
