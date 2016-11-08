package com.citycon.model.systmunits.entities;

import com.citycon.model.Grant;
/**
 * Represents all necessary information about city. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 *  to display city information.
 *
 * @author Mike
 * @version 1.1
 */
public class CityEntity extends Entity {	
	private String name;
	private String countryName;

	public String getName() {
		return name;
	}
	public String getCountryName() {
		return countryName;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
}