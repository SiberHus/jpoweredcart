package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.common.entity.catalog.Category;
import org.jpoweredcart.common.entity.catalog.CategoryToLayout;
import org.jpoweredcart.common.entity.catalog.CategoryToStore;

public interface CategoryAdminModel {

	public void addCategory(Category category);
	
	public void updateCategory(Category category);
	
	public void deleteCategory(Integer categoryId);
	
	public Category getCategory(Integer categoryId);
	
	public List<Category> getCategories(Integer parentId);
	
	public String getPath(Integer categoryId);
	
	public List<CategoryToStore> getCategoryStores(Integer categoryId);
	
	public List<CategoryToLayout> getCategoryLayouts(Integer categoryId);
	
	public int getTotalCategories();
	
	public int getTotalCategoriesByImageId(Integer imageId);
	
	public int getTotalCategoriesByLayoutId(Integer layoutId);
	
}
