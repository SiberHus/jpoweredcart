package com.jpoweredcart.admin.model.catalog;

import java.util.List;

import com.jpoweredcart.admin.form.catalog.InformationForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.catalog.Information;
import com.jpoweredcart.common.entity.catalog.InformationDesc;
import com.jpoweredcart.common.entity.catalog.InformationToLayout;
import com.jpoweredcart.common.entity.catalog.InformationToStore;

public interface InformationAdminModel {
	
	public void create(InformationForm infoForm);
	
	public void update(InformationForm infoForm);
	
	public void delete(Integer infoId);
	
	public InformationForm newForm();
	
	public InformationForm getForm(Integer infoId);
	
	public Information get(Integer infoId, Class<? extends Information> clazz);
	
	public List<Information> getList(PageParam pageParam);
	
	public List<InformationDesc> getDescriptions(Integer infoId);
	
	public List<InformationToStore> getInfoStores(Integer infoId);
	
	public List<InformationToLayout> getInfoLayouts(Integer infoId);
	
	public int getTotal();
	
	public int getTotalByLayoutId(Integer layoutId);
	
}
