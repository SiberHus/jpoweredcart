package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.Zone;
import org.jpoweredcart.common.PageParam;


public interface ZoneAdminModel {
	
	public void addZone(Zone zone);
	
	public void updateZone(Zone zone);
	
	public void saveZone(Zone zone);
	
	public void deleteZone(Integer zoneId);
	
	public Zone getZone(Integer zoneId);
	
	public List<Zone> getZonesByCountryId(Integer countryId);
	
	public List<Zone> getZones(PageParam pageParam);
	
	public int getTotalZones();
	
	public int getTotalZonesByCountryId(Integer countryId);
	
}
