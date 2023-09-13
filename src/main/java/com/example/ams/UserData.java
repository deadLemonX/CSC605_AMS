package com.example.ams;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserData {



    public static Boolean addUser(String DATABASE_URL, String TABLE_NAME, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(username TEXT PRIMARY KEY, password TEXT);";
            statement.execute(createTableSQL);
            String insertDataSQL = "INSERT INTO " + TABLE_NAME + "(username, password) VALUES " +
                    "( '" + username + "', '" + password  + "');";
            statement.execute(insertDataSQL);
            System.out.println("Data inserted successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    public static void main(String[] args) {

        final String DATABASE_URL = "jdbc:sqlite:registration_database.sqlite";
        final String TABLE_NAME = "user_registration";
        final String Username = "username";
        final String Password = "password";
        addUser(DATABASE_URL, TABLE_NAME, Username, Password);
    }
}

