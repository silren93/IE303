package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import com.javaweb.Utils.UtilsMap;
import com.javaweb.builder.BuildingSearchBuilder;

public class BuildingSearchBuilderConverter {
	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeBuilding) {
		BuildingSearchBuilder buildingSearch = new BuildingSearchBuilder.Builder()
										.setName(UtilsMap.getObject(params, "name", String.class))
										.setStreet(UtilsMap.getObject(params, "street", String.class))
										.setWard(UtilsMap.getObject(params, "ward", String.class))
										.setDistrictid(UtilsMap.getObject(params, "districtid", Integer.class))
										.setNumberofbasement(UtilsMap.getObject(params, "numberofbasement", Integer.class))
										.setFloorarea(UtilsMap.getObject(params, "floorarea", Integer.class))
										.setLevel(UtilsMap.getObject(params, "level", String.class))
										.setRentpricefrom(UtilsMap.getObject(params, "rentpricefrom", Integer.class))
										.setRentpriceto(UtilsMap.getObject(params, "rentpriceto", Integer.class))
										.setRentareafrom(UtilsMap.getObject(params, "rentareafrom", Integer.class))
										.setRentareato(UtilsMap.getObject(params, "rentareato", Integer.class))
										.setBrokeragefee(UtilsMap.getObject(params, "brokeragefee", Long.class))
										.setManagername(UtilsMap.getObject(params, "managername", String.class))
										.setManagerphonenumber(UtilsMap.getObject(params, "managerphonenumber", String.class))
										.setStaffid(UtilsMap.getObject(params, "staffid", Integer.class))
										.setTypeBuilding(typeBuilding)
										.build();
		return buildingSearch;
	}
}
