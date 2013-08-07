package com.jpoweredcart.admin.form.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.util.AutoPopulatingList;

import com.jpoweredcart.common.entity.catalog.Category;
import com.jpoweredcart.common.entity.catalog.CategoryDesc;
import com.jpoweredcart.common.entity.catalog.CategoryToLayout;
import com.jpoweredcart.common.entity.catalog.CategoryToStore;

public class CategoryForm extends Category {
	
	private static final long serialVersionUID = 1L;
	
	protected String keyword;
	
	@Valid
	protected List<CategoryDesc> descs =  new AutoPopulatingList<CategoryDesc>(CategoryDesc.class);
	
	@Valid
	protected List<CategoryToStore> stores =  new AutoPopulatingList<CategoryToStore>(CategoryToStore.class);
	
	@Valid
	protected List<CategoryToLayout> layouts =  new AutoPopulatingList<CategoryToLayout>(CategoryToLayout.class);
	
	public List<Integer> getSelectedStoreIds(){
		List<Integer> idList = new ArrayList<Integer>();
		for(CategoryToStore cts: stores){
			if(cts.getStoreId()!=null){
				idList.add(cts.getStoreId());
			}
		}
		return idList;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public List<CategoryDesc> getDescs() {
		return descs;
	}
	
	public void setDescs(List<CategoryDesc> descs) {
		this.descs = new AutoPopulatingList<CategoryDesc>(descs, CategoryDesc.class);
	}

	public List<CategoryToStore> getStores() {
		return stores;
	}

	public void setStores(List<CategoryToStore> stores) {
		this.stores = stores;
	}

	public List<CategoryToLayout> getLayouts() {
		return layouts;
	}

	public void setLayouts(List<CategoryToLayout> layouts) {
		this.layouts = layouts;
	}
	
	
}
