package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.BuildingReponsitory;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingReponsitory buildingRepository;

	@Override
	public List<BuildingDTO> findAll(String name, Long districtId) {
		// TODO Auto-generated method stub
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(name, districtId);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for (BuildingEntity item : buildingEntities) {
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName());
			building.setAdress(item.getStreet() + "," + item.getWard());
			building.setNumberOfBasement(item.getNumberOfBasement());
			result.add(building);
		}
		return result;
	}
}
