package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.bean.catalog.CategoryForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.entity.catalog.CategoryDesc;
import com.jpoweredcart.common.entity.catalog.CategoryToLayout;
import com.jpoweredcart.common.entity.catalog.CategoryToStore;

public interface CategoryAdminModel {

	public void create(CategoryForm catForm);
	
	public void update(CategoryForm catForm);
	
	public void delete(Integer catId);
	
	public CategoryForm newForm();
	
	public CategoryForm getForm(Integer catId);
	
	public Category get(Integer catId);
	
	public List<Category> getList(String separator, PageParam pageParam);
	
	/**
	 * 
	 * @param catId
	 * @param separator this value should be from #{text.separator} in language file
	 * @return
	 */
	public String getPath(Integer catId, String separator);
	
	public List<CategoryDesc> getDescriptions(Integer catId);
	
	public List<CategoryToStore> getCatStores(Integer catId);
	
	public List<CategoryToLayout> getCatLayouts(Integer catId);
	
	public int getTotal();
	
	public int getTotalByImageId(Integer imageId);
	
	public int getTotalByLayoutId(Integer layoutId);
	
}
