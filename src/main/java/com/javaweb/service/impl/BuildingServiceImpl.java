package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

    private BuildingRepository buildingRepository;

    @Override
    public List<BuildingDTO> findAll(String name) {
        // TODO Auto-generated method stub
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(name);
        List<BuildingDTO> result = new ArrayList<BuildingDTO>();
        
        for (BuildingEntity item : buildingEntities) {
            BuildingDTO building = new BuildingDTO();
            building.setName(item.getName());
            building.setAddress(item.getStreet() + "," + item.getWard());
            building.setNumberOfBasement(item.getNumberOfBasement());
            result.add(building);
        }

        return result;
    }
}

