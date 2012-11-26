package org.jpoweredcart.admin.model.localisation;

import java.util.List;

import org.jpoweredcart.admin.entity.localisation.GeoZone;
import org.jpoweredcart.common.PageParam;

public interface GeoZoneAdminModel {
	
	public void addGeoZone(GeoZone geoZone);
	
	public void updateGeoZone(GeoZone geoZone);
	
	public void saveGeoZone(GeoZone geoZone);
	
	public void deleteGeoZone(Integer geoZoneId);
	
	public GeoZone getGeoZone(Integer geoZoneId);
	
	public List<GeoZone> getGeoZones(PageParam pageParam);
	
	public int getTotalGeoZones();
	
}
