package com.jpoweredcart.admin.model.catalog.jdbc;

import java.util.List;

import com.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import com.jpoweredcart.common.BaseModel;
import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.entity.catalog.CategoryToLayout;
import com.jpoweredcart.common.entity.catalog.CategoryToStore;
import com.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class CategoryAdminModelImpl extends BaseModel implements CategoryAdminModel{

	public CategoryAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public void create(Category category) {
		
	}

	@Override
	public void update(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer categoryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category get(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getList(Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryToStore> getCatStores(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CategoryToLayout> getCatLayouts(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getTotalByImageId(Integer imageId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCategoriesByLayoutId(Integer layoutId) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
