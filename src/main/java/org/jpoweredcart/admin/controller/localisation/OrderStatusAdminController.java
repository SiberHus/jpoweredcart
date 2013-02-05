package org.jpoweredcart.admin.controller.localisation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jpoweredcart.admin.model.localisation.LanguageAdminModel;
import org.jpoweredcart.admin.model.localisation.OrderStatusAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.Language;
import org.jpoweredcart.common.entity.localisation.OrderStatuses;
import org.jpoweredcart.common.entity.localisation.OrderStatuses.OrderStatus;
import org.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import org.jpoweredcart.common.security.UserPermissions;
import org.jpoweredcart.common.service.SettingKey;
import org.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderStatusAdminController extends BaseController {
	
	@Inject
	private LanguageAdminModel languageAdminModel;
	
	@Inject
	private OrderStatusAdminModel orderStatusAdminModel;
	
	@RequestMapping(value="/admin/localisation/orderStatus")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<OrderStatus> orderStatuses = orderStatusAdminModel.getOrderStatuses(pageParam);
		model.addAttribute("orderStatuses", orderStatuses);
		
		Integer defaultOrderStatusId = getSettingService().getConfig(SettingKey.CFG_STOCK_STATUS_ID, Integer.class);
		model.addAttribute("defaultOrderStatusId", defaultOrderStatusId);
		
		int total = orderStatusAdminModel.getTotalOrderStatuses();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/localisation/orderStatus"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/localisation/orderStatusList";
	}
	
	@RequestMapping(value="/admin/localisation/orderStatus/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		List<Language> languages = languageAdminModel.getLanguages(new PageParam());
		List<OrderStatus> list = new ArrayList<OrderStatus>();
		for(Language language: languages){
			OrderStatus orderStatus = new OrderStatus();
			orderStatus.setLanguageId(language.getId());
			orderStatus.setLanguageName(language.getName());
			orderStatus.setLanguageImage(language.getImage());
			list.add(orderStatus);
		}
		OrderStatuses orderStatuses = new OrderStatuses(list);
		orderStatuses.setNewEntities(true);
		model.addAttribute("orderStatuses", orderStatuses);
		
		return "/admin/localisation/orderStatusForm";
	}
	
	@RequestMapping(value="/admin/localisation/orderStatus/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		OrderStatuses orderStatuses = orderStatusAdminModel.getOrderStatuses(id);
		orderStatuses.setNewEntities(false);
		model.addAttribute("orderStatuses", orderStatuses);
		
		return "/admin/localisation/orderStatusForm";
	}
	
	@RequestMapping(value="/admin/localisation/orderStatus/save", method=RequestMethod.POST)
	public String save(OrderStatuses orderStatuses, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(orderStatuses.hasErrors()){
			model.addAttribute("orderStatuses", orderStatuses);
			return "/admin/localisation/orderStatusForm";
		}
		
		OrderStatus orderStatusList[] = orderStatuses.getList().toArray(new OrderStatus[0]);
		if(orderStatuses.isNewEntities()){
			orderStatusAdminModel.addOrderStatus(orderStatusList);
		}else{
			orderStatusAdminModel.updateOrderStatus(orderStatusList);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/orderStatus";
	}
	
	@RequestMapping(value="/admin/localisation/orderStatus/delete", method=RequestMethod.POST)
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
				orderStatusAdminModel.deleteOrderStatus(id);
			}
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/localisation/orderStatus";
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("localisation/order_status", 
				new UnauthorizedAdminException());
	}
	
}