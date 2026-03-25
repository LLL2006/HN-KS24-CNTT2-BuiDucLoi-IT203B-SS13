package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionManager {
    private static String URL = "jdbc:mysql://localhost:3306/SS13?CreateDatabaseIfNotExist=true";
    private static String USER = "root";
    private static String PASS = "LLL2006";

    public static Connection openConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
