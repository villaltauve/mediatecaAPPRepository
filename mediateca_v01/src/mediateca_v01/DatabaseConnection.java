package mediateca_v01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://database-1.cpecymc4ejum.us-east-1.rds.amazonaws.com:3306/mediateca";
    private static final String USER = "user90";
    private static final String PASSWORD = "rdb01awspass";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

