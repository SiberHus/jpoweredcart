package com.jpoweredcart.admin.controller.sale;

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

import com.jpoweredcart.admin.form.sale.VoucherForm;
import com.jpoweredcart.admin.model.sale.VoucherAdminModel;
import com.jpoweredcart.admin.model.sale.VoucherThemeAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.Voucher;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.service.CurrencyService;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/sale/voucher")
public class VoucherAdminController extends BaseController {

	@Inject
	private CurrencyService currencyService;
	
	@Inject
	private VoucherThemeAdminModel voucherThemeAdminModel;
	
	@Inject
	private VoucherAdminModel voucherAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Voucher> voucherList = voucherAdminModel.getList(pageParam);
		model.addAttribute("vouchers", voucherList);
		int total = voucherAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/sale/voucher"));
		model.addAttribute("pagination", pagination.render());
		model.addAttribute("currency", currencyService);
		return "/admin/sale/voucherList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		addFormAttributes(voucherAdminModel.newForm(), model);
		
		return "/admin/sale/voucherForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		VoucherForm vForm = voucherAdminModel.getForm(id);
		addFormAttributes(vForm, model);
		
		return "/admin/sale/voucherForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid VoucherForm vForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			addFormAttributes(vForm, model);
			return "/admin/sale/voucherForm";
		}
		
		if(vForm.getId()!=null){
			//check existing code
			voucherAdminModel.update(vForm);
		}else{
			//check existing code
			voucherAdminModel.create(vForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/sale/voucher";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				voucherAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/sale/voucher";
	}
	
	private void addFormAttributes(VoucherForm form, Model model){
		model.addAttribute("voucherForm", form);
		model.addAttribute("voucherThemes", voucherThemeAdminModel.getList(PageParam.list()));
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("sale/voucher", 
				new UnauthorizedAdminException());
	}
}
