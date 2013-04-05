package com.jpoweredcart.admin.controller.catalog;

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

import com.jpoweredcart.admin.bean.catalog.DownloadForm;
import com.jpoweredcart.admin.model.catalog.DownloadAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Download;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;


@Controller
@RequestMapping("/admin/catalog/download")
public class DownloadAdminController extends BaseController {
	 
	@Inject
	private DownloadAdminModel downloadAdminModel; 
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Download> downloadList = downloadAdminModel.getList(pageParam);
		model.addAttribute("downloads", downloadList);
		
		int total = downloadAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/catalog/download"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/catalog/downloadList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		
		model.addAttribute("downloadForm", downloadAdminModel.newForm());
		addFormAttributes(model);
		
		return "/admin/catalog/downloadForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		
		Download dlForm = downloadAdminModel.getForm(id);
		model.addAttribute("downloadForm", dlForm);
		addFormAttributes(model);
		
		return "/admin/catalog/downloadForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid DownloadForm dlForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			System.out.println(result.getAllErrors());
			System.out.println(result.getFieldErrors());
			
			model.addAttribute("downloadForm", dlForm);
			addFormAttributes(model);
			return "/admin/catalog/downloadForm";
		}
		
		if(dlForm.getId()!=null){
			downloadAdminModel.update(dlForm);
		}else{
			downloadAdminModel.create(dlForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/catalog/download";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				downloadAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/catalog/download";
	}
	
	private void addFormAttributes(Model model){
		model.addAttribute("downloadGroups", downloadAdminModel.getList(null));
	}
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("catalog/download", 
				new UnauthorizedAdminException());
	}
	
}
