package com.javaweb.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class ConnectionUtils {
	@Value("${spring.datasource.url}")
    private static String DB_URL;

    @Value("${spring.datasource.username}")
    private static  String USER;

    @Value("${spring.datasource.password}")
    private static String PASS;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}