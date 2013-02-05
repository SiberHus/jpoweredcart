package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;
import org.jpoweredcart.common.entity.catalog.form.InformationForm;

public interface InformationAdminModel {

	public void addInformation(InformationForm info);
	
	public void updateInformation(InformationForm info);
	
	public void deleteInformation(Integer infoId);
	
	public Information getInformation(Integer infoId);
	
	public List<Information> getInformations(PageParam pageParam);
	
	public List<InformationDesc> getInformationDescriptions(Integer infoId);
	
	public List<InformationToStore> getInformationStores(Integer infoId);
	
	public List<InformationToLayout> getInformationLayouts(Integer infoId);
	
	public int getTotalInformations();
	
	public int getTotalInformationsByLayoutId(Integer layoutId);
	
}
