package com.jpoweredcart.admin.form.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.jpoweredcart.common.entity.catalog.Information;
import com.jpoweredcart.common.entity.catalog.InformationDesc;
import com.jpoweredcart.common.entity.catalog.InformationToLayout;
import com.jpoweredcart.common.entity.catalog.InformationToStore;
import org.springframework.util.AutoPopulatingList;


public class InformationForm extends Information {
	
	private static final long serialVersionUID = 1L;
	
	protected String keyword;
	
	@Valid
	protected List<InformationDesc> descs = new AutoPopulatingList<InformationDesc>(InformationDesc.class);
	
	@Valid
	protected List<InformationToStore> stores = new AutoPopulatingList<InformationToStore>(InformationToStore.class);
	
	@Valid
	protected List<InformationToLayout> layouts = new AutoPopulatingList<InformationToLayout>(InformationToLayout.class);
	
	public List<Integer> getSelectedStoreIds(){
		List<Integer> idList = new ArrayList<Integer>();
		for(InformationToStore its: stores){
			if(its.getStoreId()!=null){
				idList.add(its.getStoreId());
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
	
	public List<InformationDesc> getDescs() {
		return descs;
	}

	public void setDescs(List<InformationDesc> descs) {
		this.descs = new AutoPopulatingList<InformationDesc>(descs, InformationDesc.class);
	}
	
	public List<InformationToStore> getStores() {
		return stores;
	}
	
	public void setStores(List<InformationToStore> stores) {
		this.stores = stores;
	}
	
	public List<InformationToLayout> getLayouts() {
		return layouts;
	}
	
	public void setLayouts(List<InformationToLayout> layouts) {
		this.layouts = new AutoPopulatingList<InformationToLayout>(layouts, InformationToLayout.class);
	}
	
	
}
