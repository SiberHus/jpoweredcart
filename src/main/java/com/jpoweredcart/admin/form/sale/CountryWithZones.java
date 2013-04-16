package com.jpoweredcart.admin.form.sale;

import java.util.ArrayList;
import java.util.List;

import com.jpoweredcart.common.entity.localisation.Country;
import com.jpoweredcart.common.entity.localisation.Zone;

public class CountryWithZones extends Country {
	
	private static final long serialVersionUID = 1L;

	protected List<Zone> zones = new ArrayList<Zone>();

	public List<Zone> getZones() {
		return zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	
}
