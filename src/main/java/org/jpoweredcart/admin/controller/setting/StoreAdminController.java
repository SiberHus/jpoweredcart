package org.jpoweredcart.admin.controller.setting;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jpoweredcart.admin.entity.setting.Store;
import org.jpoweredcart.admin.model.setting.StoreAdminModel;
import org.jpoweredcart.common.BaseController;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.view.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/setting/store")
public class StoreAdminController extends BaseController {
	
	@Inject
	private StoreAdminModel storeAdminModel;
	
	@RequestMapping(value={"", "/"})
	public String index(Model model, HttpServletRequest request){
		
		PageParam pageParam = createPageParam(request);
		List<Store> storeList = storeAdminModel.getAllStores();
		model.addAttribute("stores", storeList);
		int total = storeAdminModel.getTotalStores();
		System.out.println(total);
		Pagination pagination = new Pagination();
		pagination.setTotal(total).setPageParam(pageParam)
			.setText(message(request, "text.pagination"))
			.setUrl(uri("/admin/setting/store"));
		model.addAttribute("pagination", pagination.render());
		return "/admin/setting/storeList";
	}
	
	
	
}
