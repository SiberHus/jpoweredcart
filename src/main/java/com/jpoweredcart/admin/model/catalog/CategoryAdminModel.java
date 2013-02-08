package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.common.entity.catalog.Category;
import org.jpoweredcart.common.entity.catalog.CategoryToLayout;
import org.jpoweredcart.common.entity.catalog.CategoryToStore;

public interface CategoryAdminModel {

	public void create(Category category);
	
	public void update(Category category);
	
	public void delete(Integer categoryId);
	
	public Category get(Integer categoryId);
	
	public List<Category> getList(Integer parentId);
	
	public String getPath(Integer categoryId);
	
	public List<CategoryToStore> getCatStores(Integer categoryId);
	
	public List<CategoryToLayout> getCatLayouts(Integer categoryId);
	
	public int getTotal();
	
	public int getTotalByImageId(Integer imageId);
	
	public int getTotalCategoriesByLayoutId(Integer layoutId);
	
}
