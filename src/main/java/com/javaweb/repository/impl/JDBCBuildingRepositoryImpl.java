package com.javaweb.repository.impl;



import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.Utils.ConnectionUtils;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;

@Repository
@PropertySource("classpath:application.properties")
public class JDBCBuildingRepositoryImpl implements BuildingRepository {

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String USER;

    @Value("${spring.datasource.password}")
    private String PASS;

    // Method to join tables
    public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
        Integer staffId = buildingSearchBuilder.getStaffid();
        if (staffId != null) {
            sql.append(" AND b.staffid = ").append(staffId);
        }
    }

    public static void queryNomal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        try {
            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
            for (Field item : fields) {
                item.setAccessible(true);
                String fieldName = item.getName();
                if (!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
                        && !fieldName.startsWith("rentPrice")) {
                    Object value = item.get(buildingSearchBuilder);
                    if (value != null) {
                        if (item.getType().getName().equals("java.lang.Long") || 
                            item.getType().getName().equals("java.lang.Integer") || 
                            item.getType().getName().equals("java.lang.Float")) {
                            where.append(" AND b." + fieldName + " = " + value);
                        } else if (item.getType().getName().equals("java.lang.String")) {
                            where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        Integer staffId = buildingSearchBuilder.getStaffid();
        if (staffId != null) {
            where.append(" AND assignmentbuilding.staffid = ").append(staffId);
        }

        Integer rentAreaFrom = buildingSearchBuilder.getRentareafrom();
        Integer rentAreaTo = buildingSearchBuilder.getRentareato();
        if (rentAreaFrom != null || rentAreaTo != null) {
            where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid");
            if (rentAreaFrom != null) {
                where.append(" AND r.value >= ").append(rentAreaFrom);
            }
            if (rentAreaTo != null) {
                where.append(" AND r.value <= ").append(rentAreaTo);
            }
            where.append(")");
        }

        Integer rentPriceFrom = buildingSearchBuilder.getRentpricefrom();
        Integer rentPriceTo = buildingSearchBuilder.getRentpriceto();
        if (rentPriceFrom != null || rentPriceTo != null) {
            if (rentPriceFrom != null) {
                where.append(" AND b.rentprice >= ").append(rentPriceFrom);
            }
            if (rentPriceTo != null) {
                where.append(" AND b.rentprice <= ").append(rentPriceTo);
            }
        }

        List<String> typeCode = buildingSearchBuilder.getTypeCode();
        if (typeCode != null && !typeCode.isEmpty()) {
            where.append(" AND (");
            String condition = typeCode.stream()
                .map(it -> "renttype.code LIKE '%" + it + "%'")
                .collect(Collectors.joining(" OR "));
            where.append(condition);
            where.append(")");
        }
    }


    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
        StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, b.managername, b.managerphonenumber FROM building b ");
        joinTable(buildingSearchBuilder, sql);
        StringBuilder where = new StringBuilder(" WHERE 1=1 ");
        queryNomal(buildingSearchBuilder, where);
        querySpecial(buildingSearchBuilder, where);
        where.append(" GROUP BY b.id;");
        sql.append(where);
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionUtils.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {

			while(rs.next()){
			    BuildingEntity buildingEntity = new BuildingEntity();
			    buildingEntity.setId(rs.getInt("b.id"));
			    buildingEntity.setName(rs.getString("b.name"));
			    buildingEntity.setWard(rs.getString("b.ward"));
			    
			    int districtId = rs.getInt("b.districtid");
//			    DistrictEntity districtEntity = getDistrictById(districtId); // A method to fetch the district by its ID

			    // Set the DistrictEntity object to BuildingEntity
//			    buildingEntity.setDistrictEntity(districtEntity); // Use a method to fetch the district by its ID
//			    buildingEntity.setDistrictEntity(districtEntity);
			    buildingEntity.setStreet(rs.getString("b.street"));
//			    buildingEntity.setFloorarea(rs.getInt("b.floorarea"));
			    buildingEntity.setRentprice(rs.getInt("b.rentprice"));
//			    buildingEntity.setServicefee(rs.getString("b.servicefee"));
//			    buildingEntity.setBrokeragefee(rs.getLong("b.brokeragefee"));
			    buildingEntity.setManagername(rs.getString("b.managername"));
			    buildingEntity.setManagerphonenumber(rs.getString("b.managerphonenumber"));
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