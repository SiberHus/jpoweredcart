package org.jpoweredcart.admin.model.catalog.jdbc;

import java.util.List;

import org.jpoweredcart.admin.model.catalog.CategoryAdminModel;
import org.jpoweredcart.common.BaseModel;
import org.jpoweredcart.common.entity.catalog.Category;
import org.jpoweredcart.common.entity.catalog.CategoryToLayout;
import org.jpoweredcart.common.entity.catalog.CategoryToStore;
import org.jpoweredcart.common.service.SettingService;
import org.springframework.jdbc.core.JdbcOperations;

public class CategoryAdminModelImpl extends BaseModel implements CategoryAdminModel{

	public CategoryAdminModelImpl(SettingService settingService, JdbcOperations jdbcOperations){
		super(settingService, jdbcOperations);
	}
	
	@Override
	public void addCategory(Category category) {
		
	}

	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category> getCategories(Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryToStore> getCategoryStores(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CategoryToLayout> getCategoryLayouts(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalCategories() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getTotalCategoriesByImageId(Integer imageId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalCategoriesByLayoutId(Integer layoutId) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
