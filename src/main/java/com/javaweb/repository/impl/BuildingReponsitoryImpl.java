package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.Utils.ConnectionUtils;
import com.javaweb.Utils.UtilsCheckNumber;
import com.javaweb.Utils.UtilsCheckString;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
public class BuildingReponsitoryImpl implements BuildingRepository {

	public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		String staffId = (String) params.get("staffId");
		if (UtilsCheckString.checkString(staffId)) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}

		if (typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}

		String rentAreaTo = (String) params.get("areaTo");
		String rentAreaFrom = (String) params.get("areaFrom");

		if (UtilsCheckString.checkString(rentAreaFrom) == true || UtilsCheckString.checkString(rentAreaTo) == true) {
			sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
		}
	}

	public static void queryNomal(Map<String, Object> params, StringBuilder where) {
		for (Map.Entry<String, Object> it : params.entrySet()) {
			if (!it.getKey().equals("staffId") && it.getKey().equals("typeCode") && it.getKey().startsWith("area")
					&& it.getKey().startsWith("rentPrice")) {

				String value = it.getValue().toString();
				if (UtilsCheckString.checkString(value)) {
					if (UtilsCheckNumber.isNumber(value) == true) {
						where.append(" AND b." + it.getKey() + " = " + value);
					} else {
						where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
					}
				}
			}
		}
	}

	public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String staffId = (String) params.get("staffId");
		if (UtilsCheckString.checkString(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}

		String rentAreaTo = (String) params.get("areaTo");
		String rentAreaFrom = (String) params.get("areaFrom");

		if (UtilsCheckString.checkString(rentAreaFrom) == true || UtilsCheckString.checkString(rentAreaTo) == true) {
			if (UtilsCheckString.checkString(rentAreaFrom)) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if (UtilsCheckString.checkString(rentAreaTo)) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
		}
		String rentPriceTo = (String) params.get("rentPriceTo");
		String rentPriceFrom = (String) params.get("rentPriceFrom");

		if (UtilsCheckString.checkString(rentPriceTo) == true || UtilsCheckString.checkString(rentPriceFrom) == true) {
			if (UtilsCheckString.checkString(rentAreaFrom)) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if (UtilsCheckString.checkString(rentAreaTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		if (typeCode != null && typeCode.size() != 0) {
		    List<String> code = new ArrayList<>();
		    for (String item : typeCode) {
		        code.add("\"" + item + "\"");
		    }
		    where.append(" AND renttype.code IN (" + String.join(",", code) + ") ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(params, where);
		querySpecial(params, typeCode, where);
		sql.append(where); // Add WHERE condition to SQL query
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionUtils.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {

			while(rs.next()){
			    BuildingEntity buildingEntity = new BuildingEntity();
			    buildingEntity.setId(rs.getLong("b.id"));
			    buildingEntity.setName(rs.getString("b.name"));
			    buildingEntity.setWard(rs.getString("b.ward"));
			    buildingEntity.setDistrictid(rs.getLong("b.districtid"));
			    buildingEntity.setStreet(rs.getString("b.street"));
			    buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
			    buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
			    buildingEntity.setServiceFee(rs.getString("b.servicefee"));
			    buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
			    buildingEntity.setManagerName(rs.getString("b.managername"));
			    buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
			    result.add(buildingEntity);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}

	@Override
	public void DeleteById(Long id) {
		// TODO Auto-generated method stub

	}

}
