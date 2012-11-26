package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.LengthClass;
import org.jpoweredcart.admin.entity.localisation.LengthClassDesc;
import org.jpoweredcart.common.PageParam;

public interface LengthClassAdminModel {

	public void addLengthClass(LengthClass lengthClass);
	
	public void updateLengthClass(LengthClass lengthClass); 
	
	public void deleteLengthClass(Integer lengthClassId);
	
	public LengthClass getLengthClass(Integer lengthClassId);
	
	public List<LengthClass> getLengthClasses(PageParam pageParam);
	
	public List<LengthClassDesc> getLengthClassDescsByUnit(String unit);
	
	public List<LengthClassDesc> getLengthClassDescs(Integer lengthClassId);
	
	public int getTotalLengthClasses();
	
}
