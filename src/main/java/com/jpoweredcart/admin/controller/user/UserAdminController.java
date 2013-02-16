package com.jpoweredcart.admin.controller.user;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpoweredcart.admin.bean.user.UserForm;
import com.jpoweredcart.admin.model.user.UserAdminModel;
import com.jpoweredcart.admin.model.user.UserGroupAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;

@Controller
@RequestMapping("/admin/user/user")
public class UserAdminController extends BaseController {
	
	@Inject
	private UserAdminModel userAdminModel;
	
	@Inject
	private UserGroupAdminModel userGroupAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<User> userList = userAdminModel.getList(pageParam);
		model.addAttribute("users", userList);
		
		int total = userAdminModel.getTotal();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPage(pageParam.getPage())
			.setLimit(pageParam.getLimit()).setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/user/user"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/user/userList";
	}
	
	
	@RequestMapping(value="/create")
	public String create(Model model){
		
		UserPermissions.checkModify("user/user", 
				new UnauthorizedAdminException());
		initUserForm(model);
		
		model.addAttribute("userForm", new UserForm());
		
		return "/admin/user/userForm";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		initUserForm(model);
		
		UserForm userForm = userAdminModel.getForm(id);
		model.addAttribute("userForm", userForm);
		
		return "/admin/user/userForm";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@Valid UserForm userForm, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(userForm.getId()==null){
			//new user
			try{
				userAdminModel.getOneByUsername(userForm.getUsername());
				model.addAttribute("msg_warning", "error.exists");
			}catch(EmptyResultDataAccessException e){}
			if(StringUtils.isBlank(userForm.getPassword())){
				result.rejectValue("password", "error.password");
			}else if(!userForm.getPassword().equals(userForm.getConfirm())){
				result.rejectValue("confirm", "error.confirm");
			}
		}else{
			if(!StringUtils.isBlank(userForm.getPassword())){
				if(!userForm.getPassword().equals(userForm.getConfirm())){
					result.rejectValue("confirm", "error.confirm");
				}
			}
		}
		
		if(result.hasErrors()){
			initUserForm(model);
			model.addAttribute("userForm", userForm);
			return "/admin/user/userForm";
		}
		
		if(userForm.getId()!=null){
			userAdminModel.update(userForm);
		}else{
			userAdminModel.create(userForm);
		}
		
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/user/user";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@RequestParam(value="selected", required=false) Integer[] ids, Model model,
			RedirectAttributes redirect){
		checkModifyPermission();
		boolean error = false;
		if(ids!=null){
			for(Integer id: ids){
				if(UserPermissions.getUserId().equals(id)){
					error = true;
					redirect.addFlashAttribute("msg_warning", "error.account");
				}
			}
			if(!error) for(Integer id: ids){
				userAdminModel.delete(id);
			}
			if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		}
		
		return "redirect:/admin/user/user";
	}
	
	private void initUserForm(Model model){
		//should not over 50
		model.addAttribute("userGroups", userGroupAdminModel
				.getList(PageParam.list(50)));
	}
	
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("user/user", 
				new UnauthorizedAdminException());
	}
	
}




