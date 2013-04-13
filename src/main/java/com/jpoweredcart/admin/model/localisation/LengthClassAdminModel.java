package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.admin.form.localisation.LengthClassForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.LengthClass;
import com.jpoweredcart.common.entity.localisation.LengthClassDesc;

public interface LengthClassAdminModel {

	public void create(LengthClassForm lcForm);
	
	public void update(LengthClassForm lcForm); 
	
	public void delete(Integer lcId);
	
	public LengthClassForm newForm();
	
	public LengthClassForm getForm(Integer lcId);
	
	public LengthClass get(Integer lcId);
	
	public List<LengthClass> getList(PageParam pageParam);
	
	public List<LengthClassDesc> getDescriptionsByUnit(String unit);
	
	public List<LengthClassDesc> getDescriptions(Integer lcId);
	
	public int getTotal();
	
}
