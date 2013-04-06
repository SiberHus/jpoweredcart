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

import com.jpoweredcart.admin.bean.sale.VoucherThemeForm;
import com.jpoweredcart.admin.model.sale.VoucherThemeAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.sale.VoucherTheme;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.service.media.DefaultMediaService;
import com.jpoweredcart.common.service.media.MediaService;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/sale/voucherTheme")
public class VoucherThemeAdminController extends BaseController {

	@Inject
	private MediaService mediaService;
	
	@Inject
	private VoucherThemeAdminModel voucherThemeAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<VoucherTheme> voucherThemeList = voucherThemeAdminModel.getList(pageParam);
		model.addAttribute("voucherThemes", voucherThemeList);
		int total = voucherThemeAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/sale/voucherTheme"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/sale/voucherThemeList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		addFormAttributes(voucherThemeAdminModel.newForm(), model);
		
		return "/admin/sale/voucherThemeForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		VoucherThemeForm vtForm = voucherThemeAdminModel.getForm(id);
		addFormAttributes(vtForm, model);
		
		return "/admin/sale/voucherThemeForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid VoucherThemeForm vtForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			addFormAttributes(vtForm, model);
			return "/admin/sale/voucherThemeForm";
		}
		
		if(vtForm.getId()!=null){
			voucherThemeAdminModel.update(vtForm);
		}else{
			voucherThemeAdminModel.create(vtForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/sale/voucherTheme";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				voucherThemeAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/sale/voucherTheme";
	}
	
	private void addFormAttributes(VoucherThemeForm form, Model model){
		
		model.addAttribute("voucherThemeForm", form);
		
		String imgUrl = mediaService.getThumbnailUrl(form.getImage());
		model.addAttribute("thumb", imgUrl);
		String noImgUrl = mediaService.getImageUrl(DefaultMediaService.NO_IMG_PATH);
		model.addAttribute("noImg", noImgUrl);
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("sale/voucher_theme", 
				new UnauthorizedAdminException());
	}
}
