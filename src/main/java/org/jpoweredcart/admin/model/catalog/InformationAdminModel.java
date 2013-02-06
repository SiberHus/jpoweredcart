package org.jpoweredcart.admin.model.catalog;

import java.util.List;

import org.jpoweredcart.admin.form.catalog.InformationForm;
import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.catalog.Information;
import org.jpoweredcart.common.entity.catalog.InformationDesc;
import org.jpoweredcart.common.entity.catalog.InformationToLayout;
import org.jpoweredcart.common.entity.catalog.InformationToStore;

public interface InformationAdminModel {

	public void addInformation(InformationForm informationForm);
	
	public void updateInformation(InformationForm informationForm);
	
	public void deleteInformation(Integer informationId);
	
	public Information getInformation(Integer informationId);
	
	public List<Information> getInformations(PageParam pageParam);
	
	public List<InformationDesc> getInformationDescriptions(Integer informationId);
	
	public List<InformationToStore> getInformationStores(Integer informationId);
	
	public List<InformationToLayout> getInformationLayouts(Integer informationId);
	
	public int getTotalInformations();
	
	public int getTotalInformationsByLayoutId(Integer layoutId);
	
}
