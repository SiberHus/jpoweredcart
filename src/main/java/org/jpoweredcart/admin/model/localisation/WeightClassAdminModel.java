package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.WeightClass;
import org.jpoweredcart.common.entity.localisation.WeightClassDesc;

public interface WeightClassAdminModel {

	public void create(WeightClass weightClass);
	
	public void update(WeightClass weightClass); 
	
	public void delete(Integer weightClassId);
	
	public WeightClass get(Integer weightClassId);
	
	public List<WeightClass> getList(PageParam pageParam);
	
	public List<WeightClassDesc> getWeightClassDescsByUnit(String unit);
	
	public List<WeightClassDesc> getWeightClassDescs(Integer weightClassId);
	
	public int getTotal();
	
}
