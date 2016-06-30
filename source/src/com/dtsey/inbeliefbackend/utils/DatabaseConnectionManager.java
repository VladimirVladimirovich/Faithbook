package com.dtsey.inbeliefbackend.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

    /*
    private static final String DATABASE_USERNAME = "inBeliefBackend";
    private static final String DATABASE_PASSWORD = "pvXdamdjyGQfmSYR";
    private static final String DATABASE_URL = "jdbc:mysql://mysql79557-env-6678332.jelastic.regruhosting.ru/inbelief";
    */
   
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/inbelief";
   

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_NAME);

            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Error while connection: " + e.getMessage());

            return null;
        }
    }
}