package com.example.ams_scheduler;

import java.sql.*;

public class MaintenancePersonnel {

    public static Boolean addWorker(String DATABASE_URL, String TABLE_NAME, int maintenanceID, String workerName, Boolean certification) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(MaintenanceID TEXT PRIMARY KEY, WorkerName TEXT, Certification TEXT);";
            statement.execute(createTableSQL);
            String insertDataSQL = "INSERT INTO " + TABLE_NAME + "(MaintenanceID, WorkerName, Certification) VALUES " +
                    "( '" + maintenanceID + "', '" + workerName + "', '" + certification + "');";
            statement.execute(insertDataSQL);
            System.out.println("Data inserted successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static void clearMaintenanceTable(String DATABASE_URL, String TABLE_NAME) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            String dropTableSQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
            statement.execute(dropTableSQL);

            System.out.println("Table deleted successfully.");
          //  return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
          //  return false;
        }
    }

}
