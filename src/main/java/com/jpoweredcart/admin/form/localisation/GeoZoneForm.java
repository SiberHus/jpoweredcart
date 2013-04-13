package com.jpoweredcart.admin.form.localisation;

import java.util.ArrayList;
import java.util.List;

import com.jpoweredcart.common.entity.localisation.GeoZone;
import com.jpoweredcart.common.entity.localisation.ZoneToGeoZone;

public class GeoZoneForm extends GeoZone {
	
	private static final long serialVersionUID = 1L;

	protected List<ZoneToGeoZone> zoneToGeoZones = new ArrayList<ZoneToGeoZone>();
	
	public List<ZoneToGeoZone> getZoneToGeoZones() {
		return zoneToGeoZones;
	}
	
	public void setZoneToGeoZones(List<ZoneToGeoZone> zoneToGeoZones) {
		this.zoneToGeoZones = zoneToGeoZones;
	}
	
}
