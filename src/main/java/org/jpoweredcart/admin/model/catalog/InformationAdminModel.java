package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.admin.form.catalog.InformationForm;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;

public interface InformationAdminModel {
	
	public void create(InformationForm infoForm);
	
	public void update(InformationForm infoForm);
	
	public void delete(Integer infoId);
	
	public InformationForm getForm(Integer infoId);
	
	public Information get(Integer infoId);
	
	public List<Information> getList(PageParam pageParam);
	
	public List<InformationDesc> getInfoDescriptions(Integer infoId);
	
	public List<InformationToStore> getInfoStores(Integer infoId);
	
	public List<InformationToLayout> getInfoLayouts(Integer infoId);
	
	public int getTotal();
	
	public int getTotalByLayoutId(Integer layoutId);
	
}
