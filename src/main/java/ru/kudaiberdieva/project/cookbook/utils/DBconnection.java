package ru.kudaiberdieva.project.cookbook.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/cookBook";
    private static final String USER = "postgres";
    private static final String PASS = "pass";
    private Connection connection;

    public Connection connection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASS);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

