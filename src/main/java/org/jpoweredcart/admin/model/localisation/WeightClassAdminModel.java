package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.WeightClass;
import org.jpoweredcart.common.entity.localisation.WeightClassDesc;

public interface WeightClassAdminModel {

	public void addWeightClass(WeightClass weightClass);
	
	public void updateWeightClass(WeightClass weightClass); 
	
	public void deleteWeightClass(Integer weightClassId);
	
	public WeightClass getWeightClass(Integer weightClassId);
	
	public List<WeightClass> getWeightClasses(PageParam pageParam);
	
	public List<WeightClassDesc> getWeightClassDescsByUnit(String unit);
	
	public List<WeightClassDesc> getWeightClassDescs(Integer weightClassId);
	
	public int getTotalWeightClasses();
	
}
