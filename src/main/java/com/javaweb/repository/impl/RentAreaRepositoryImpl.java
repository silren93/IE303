package com.javaweb.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
    @Override
    public List<RentAreaEntity> getValueByBuildingId(Long id) {
        String sql = "SELECT * FROM rentarea WHERE rentarea.buildingid = " + id;
        List<RentAreaEntity> rentAreas = new ArrayList<>();
        return null;
    }
}
