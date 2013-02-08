package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.common.PageParam;
import org.jpoweredcart.common.entity.localisation.LengthClass;
import org.jpoweredcart.common.entity.localisation.LengthClassDesc;

public interface LengthClassAdminModel {

	public void create(LengthClass lengthClass);
	
	public void update(LengthClass lengthClass); 
	
	public void delete(Integer lengthClassId);
	
	public LengthClass get(Integer lengthClassId);
	
	public List<LengthClass> getList(PageParam pageParam);
	
	public List<LengthClassDesc> getLengthClassDescsByUnit(String unit);
	
	public List<LengthClassDesc> getLengthClassDescs(Integer lengthClassId);
	
	public int getTotal();
	
}
