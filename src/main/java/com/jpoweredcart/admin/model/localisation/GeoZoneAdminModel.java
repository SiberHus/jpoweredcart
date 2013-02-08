package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.GeoZone;

public interface GeoZoneAdminModel {
	
	public void create(GeoZone geoZone);
	
	public void update(GeoZone geoZone);
	
	public void delete(Integer geoZoneId);
	
	public GeoZone get(Integer geoZoneId);
	
	public List<GeoZone> getList(PageParam pageParam);
	
	public int getTotal();
	
}
