package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.Zone;


public interface ZoneAdminModel {
	
	public void create(Zone zone);
	
	public void update(Zone zone);
	
	public void delete(Integer zoneId);
	
	public Zone get(Integer zoneId);
	
	public List<Zone> getAllByCountryId(Integer countryId);
	
	public List<Zone> getList(PageParam pageParam);
	
	public int getTotal();
	
	public int getTotalByCountryId(Integer countryId);
	
}
