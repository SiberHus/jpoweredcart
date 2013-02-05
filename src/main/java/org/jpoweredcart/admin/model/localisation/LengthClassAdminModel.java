package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.LengthClass;
import org.jpoweredcart.common.entity.localisation.LengthClassDesc;

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
