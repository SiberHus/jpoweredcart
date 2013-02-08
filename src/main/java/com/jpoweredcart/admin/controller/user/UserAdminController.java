package com.jpoweredcart.admin.controller.user;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import com.jpoweredcart.admin.model.user.UserAdminModel;
import com.jpoweredcart.admin.model.user.UserGroupAdminModel;
import com.jpoweredcart.common.BaseController;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.user.User;
import com.jpoweredcart.common.exception.admin.UnauthorizedAdminException;
import com.jpoweredcart.common.security.UserPermissions;
import com.jpoweredcart.common.view.Pagination;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserAdminController extends BaseController {
	
	@Inject
	private UserAdminModel userAdminModel;
	
	@Inject
	private UserGroupAdminModel userGroupAdminModel;
	
	@RequestMapping(value="/admin/user/user")
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<User> userList = userAdminModel.getUsers(pageParam);
		model.addAttribute("users", userList);
		
		int total = userAdminModel.getTotalUsers();
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPage(pageParam.getPage())
			.setLimit(pageParam.getLimit()).setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/user/user"));
		model.addAttribute("pagination", pagination.render());
		
		return "/admin/user/userList";
	}
	
	
	@RequestMapping(value="/admin/user/user/create")
	public String create(Model model){
		
		UserPermissions.checkModify("user/user", 
				new UnauthorizedAdminException());
		initUserForm(model);
		
		model.addAttribute("user", new User());
		
		return "/admin/user/userForm";
	}
	
	@RequestMapping(value="/admin/user/user/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model){
		
		checkModifyPermission();
		initUserForm(model);
		
		User user = userAdminModel.get(id);
		model.addAttribute("user", user);
		
		return "/admin/user/userForm";
	}
	
	@RequestMapping(value="/admin/user/user/save", method=RequestMethod.POST)
	public String save(@Valid User user, BindingResult result, Model model, 
			RedirectAttributes redirect){
		
		checkModifyPermission();
		
		if(user.getId()==null){
			//new user
			try{
				userAdminModel.getOneByUsername(user.getUsername());
				model.addAttribute("msg_warning", "error.exists");
			}catch(EmptyResultDataAccessException e){}
			if(StringUtils.isBlank(user.getPassword())){
				result.rejectValue("password", "error.password");
			}else if(!user.getPassword().equals(user.getConfirm())){
				result.rejectValue("confirm", "error.confirm");
			}
		}else{
			if(!StringUtils.isBlank(user.getPassword())){
				if(!user.getPassword().equals(user.getConfirm())){
					result.rejectValue("confirm", "error.confirm");
				}
			}
		}
		
		if(result.hasErrors()){
			initUserForm(model);
			model.addAttribute("user", user);
			return "/admin/user/userForm";
		}
		
		userAdminModel.saveUser(user);
		
		redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/user/user";
	}
	
	@RequestMapping(value="/admin/user/user/delete", method=RequestMethod.POST)
	public String delete(@RequestParam("selected") Integer[] ids, Model model,
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
		}
		if(!error) redirect.addFlashAttribute("msg_success", "text.success");
		
		return "redirect:/admin/user/user";
	}
	
	private void initUserForm(Model model){
		//should not over 50
		model.addAttribute("userGroups", userGroupAdminModel
				.getUserGroups(PageParam.list(50)));
	}
	
	
	private void checkModifyPermission(){
		UserPermissions.checkModify("user/user", 
				new UnauthorizedAdminException());
	}
	
}




