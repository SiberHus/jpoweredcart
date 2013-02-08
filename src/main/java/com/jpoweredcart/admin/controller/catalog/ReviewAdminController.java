package com.jpoweredcart.admin.controller.catalog;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jpoweredcart.admin.bean.catalog.ReviewForm;
import com.jpoweredcart.admin.model.catalog.ReviewAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Review;
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
@RequestMapping("/admin/catalog/review")
public class ReviewAdminController extends BaseController {

	@Inject
	private ReviewAdminModel reviewAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		PageParam pageParam = createPageParam(request);
		List<Review> infoList = reviewAdminModel.getList(pageParam);
		model.addAttribute("reviews", infoList);
		
		int total = reviewAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/catalog/review"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/catalog/reviewList";
	}
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		checkModifyPermission();
		model.addAttribute("reviewForm", reviewAdminModel.newForm());
		
		return "/admin/catalog/reviewForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		ReviewForm reviewForm = reviewAdminModel.getForm(id);
		model.addAttribute("reviewForm", reviewForm);
		
		return "/admin/catalog/reviewForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid ReviewForm reviewForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(result.hasErrors()){
			model.addAttribute("reviewForm", reviewForm);
			return "/admin/catalog/reviewForm";
		}
		
		if(reviewForm.getId()!=null){
			reviewAdminModel.update(reviewForm);
		}else{
			reviewAdminModel.create(reviewForm);
		}
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/catalog/review";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected",required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			if(!error) for(Integer id: ids){
				reviewAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/catalog/review";
	}
	
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("catalog/review", 
				new UnauthorizedAdminException());
	}
}
