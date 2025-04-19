package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.Utils.ExceptionUtils;
import com.javaweb.repository.BuildingReponsitory;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingReponsitoryImpl implements BuildingReponsitory {
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "KTr#932409";

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		StringBuilder sql = new StringBuilder(
				"SELECT b.id, b.name, b.street, b.ward, b.districtid, b.numberofbasement, b.numberofbasement, b.floorarea, b.rentprice, \"\r\n"
						+ "    + \"b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee");
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());

			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setName(rs.getString("name"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setNumberOfBasement(rs.getInt("numberOfbasement"));
				result.add(building);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
			ExceptionUtils.rethrowRuntimeException(e); // Xử lý ngoại lệ với ExceptionUtils
		}
		return result;
	}

	@Override
	public void DeleteById(Long id) {
		// TODO Auto-generated method stub

	}

}
