package org.jpoweredcart.common.entity.catalog.form;

import java.util.List;

import javax.validation.Valid;

import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;
import org.springframework.util.AutoPopulatingList;


public class InformationForm extends Information {
	
	private static final long serialVersionUID = 1L;
	
	@Valid
	protected List<InformationDesc> descs = new AutoPopulatingList<InformationDesc>(InformationDesc.class);
	
	@Valid
	protected List<InformationToStore> stores = new AutoPopulatingList<InformationToStore>(InformationToStore.class);
	
	@Valid
	protected List<InformationToLayout> layouts = new AutoPopulatingList<InformationToLayout>(InformationToLayout.class);
	
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
		this.stores = new AutoPopulatingList<InformationToStore>(stores, InformationToStore.class);
	}
	
	public List<InformationToLayout> getLayouts() {
		return layouts;
	}

	public void setLayouts(List<InformationToLayout> layouts) {
		this.layouts = new AutoPopulatingList<InformationToLayout>(layouts, InformationToLayout.class);
	}

}
