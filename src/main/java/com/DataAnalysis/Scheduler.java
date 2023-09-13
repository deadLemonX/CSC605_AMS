package com.example.ams;

import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import java.sql.*;
import java.util.ArrayList;

public class Scheduler {
    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\MikeJ\\IdeaProjects\\AMS_Scheduler\\AMS_Scheduler";


    public static Boolean addAppointment(String DATABASE_URL, String TABLE_NAME, int applianceID, String applianceType, String applianceStatus, Date scheduleDate, String maintenanceID) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {


            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(ApplianceID TEXT PRIMARY KEY, Type TEXT, Status TEXT, ScheduleDate DATE, MaintenanceID TEXT);";
            statement.execute(createTableSQL);
            String insertDataSQL = "INSERT INTO " + TABLE_NAME + "(ApplianceID, Type, Status, ScheduleDate, MaintenanceID) VALUES " +
                    "( '" + applianceID + "', '" + applianceType + "', '" + applianceStatus + "', '" + scheduleDate + "', '" + maintenanceID + "');";
            statement.execute(insertDataSQL);
            System.out.println("Data inserted successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public static void clearScheduleTable(String DATABASE_URL, String TABLE_NAME) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {

            String dropTableSQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
            statement.execute(dropTableSQL);

            System.out.println("Table deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}