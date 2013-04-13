package com.jpoweredcart.admin.model.localisation;

import java.util.List;

import com.jpoweredcart.admin.form.localisation.GeoZoneForm;
import com.jpoweredcart.common.PageParam;
import com.jpoweredcart.common.entity.localisation.GeoZone;

public interface GeoZoneAdminModel {
	
	public void create(GeoZoneForm geoZoneForm);
	
	public void update(GeoZoneForm geoZoneForm);
	
	public void delete(Integer geoZoneId);
	
	public GeoZone newForm();
	
	public GeoZoneForm getForm(Integer geoZoneId);
	
	public GeoZone get(Integer geoZoneId);
	
	public List<GeoZone> getList(PageParam pageParam);
	
	public int getTotal();
	
}
