package com.javaweb.repository;

import java.util.List;

import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingReponsitory {
	List<BuildingEntity> findAll(String name, Long districtId);
	void DeleteById(Long id);
}
