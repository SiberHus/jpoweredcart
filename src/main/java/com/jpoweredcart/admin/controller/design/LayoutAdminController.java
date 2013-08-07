package com.jpoweredcart.admin.controller.design;

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

import com.jpoweredcart.admin.form.design.LayoutForm;
import com.jpoweredcart.admin.model.design.LayoutAdminModel;
import com.jpoweredcart.admin.model.setting.StoreAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.design.Layout;
import com.jpoweredcart.common.entity.design.LayoutRoute;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/design/layout")
public class LayoutAdminController extends BaseController {
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@Inject
	private LayoutAdminModel layoutAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Layout> currencies = layoutAdminModel.getList(pageParam);
		model.addAttribute("layouts", currencies);
		int total = layoutAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/design/layout"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/design/layoutList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model, HttpServletRequest request){
		
		checkModifyPermission();
		
		addFormAttributes(layoutAdminModel.newForm(), model);
		
		return "/admin/design/layoutForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		LayoutForm layoutForm = layoutAdminModel.getForm(id);
		addFormAttributes(layoutForm, model);
		
		return "/admin/design/layoutForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid LayoutForm layoutForm, BindingResult result, Model model, 
			RedirectAttributes redirect, HttpServletRequest request){
		
		checkModifyPermission();
		
		String storeIds[] = request.getParameterValues("layoutRoutes.storeId");
		String routes[] = request.getParameterValues("layoutRoutes.route");
		if(storeIds!=null){
			//i=1 because the first one is dummy value from prototype
			for(int i=1;i<storeIds.length;i++){
				int storeId = getConversionService().convert(storeIds[i], Integer.class);
				LayoutRoute layoutRoute = new LayoutRoute();
				layoutRoute.setStoreId(storeId);
				layoutRoute.setRoute(routes[i]);
				layoutForm.getLayoutRoutes().add(layoutRoute);
			}
		}
		
		if(result.hasErrors()){
			addFormAttributes(layoutForm, model);
			return "/admin/design/layoutForm";
		}
		
		if(layoutForm.getId()!=null){
			layoutAdminModel.update(layoutForm);
		}else{
			layoutAdminModel.create(layoutForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/design/layout";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected", required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				layoutAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/design/layout";
	}
	
	private void addFormAttributes(LayoutForm form, Model model){
		
		model.addAttribute("layoutForm", form);
		model.addAttribute("stores", storeAdminModel.getAll());
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("design/layout", 
				new UnauthorizedAdminException());
	}
}
