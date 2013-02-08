package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.TaxClass;

public interface TaxClassAdminModel {

	
	public void create(TaxClass taxClass);
	
	public void update(TaxClass taxClass);
	
	public void delete(Integer taxClassId);
	
	public TaxClass get(Integer taxClassId);
	
	public List<TaxClass> getList(PageParam pageParam);
	
	public int getTotal();
	
}
