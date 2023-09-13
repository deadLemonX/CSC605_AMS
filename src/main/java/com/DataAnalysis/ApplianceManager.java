package com.DataAnalysis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;

public class ApplianceManager {

    public Boolean addAppliance(String DATABASE_URL, String TABLE_NAME, int applianceID, String applianceType, String applianceBrand, String applianceModel, String applianceLocation, String applianceImageLink, String applianceStatus, String dateOfInstall, String alias) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ApplianceID TEXT PRIMARY KEY, Type TEXT, Brand TEXT, Model TEXT, Location TEXT, ImageLink TEXT, Status TEXT, DateOfInstall TEXT, Alias TEXT);";
            statement.execute(createTableSQL);
            String insertDataSQL = "INSERT INTO " + TABLE_NAME + "(ApplianceID, Type, Brand, Model, Location, ImageLink, Status, DateOfInstall, Alias) VALUES " +
                    "( '" + applianceID + "', '" + applianceType + "', '" + applianceBrand + "', '" + applianceModel + "', '" + applianceLocation + "', '" + applianceImageLink + "', '" + applianceStatus + "', '" + dateOfInstall + "', '" + alias + "');";
            statement.execute(insertDataSQL);
            System.out.println("Data inserted successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Boolean addApplianceTypeImageLink(String DATABASE_URL, String TABLE_NAME, String applianceType, String applianceImageLink) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(Type TEXT PRIMARY KEY, ImageLink TEXT);";
            statement.execute(createTableSQL);
            String insertDataSQL = "INSERT INTO " + TABLE_NAME + "(Type, ImageLink) VALUES " +
                    "( '" + applianceType + "', '" + applianceImageLink + "');";
            statement.execute(insertDataSQL);
            System.out.println("Data inserted successfully.");
            return true;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    public String checkServiceStatus(LocalDate inputDate) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);

        // Calculate the difference in years between the current date and input date
        Period period = Period.between(inputDate, currentDate);

        // Check if the input date is 1 year or more in the past
        System.out.println(period.getYears());
        if (period.getYears() >= 1) {
            return "Needs Service";
        } else {
            return "Good";
        }
    }



/*
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager("jdbc:sqlite:/Users/matthewrivera/Projects/AMS/AMS_Database");
        final String DATABASE_URL = databaseManager.DATABASE_URL;
        //final String TABLE_NAME_APPLIANCE_DATA = "ApplianceData";
        final String TABLE_NAME_IMAGE_DATA = "ApplianceImageTypeData";

       ApplianceManager applianceManager = new ApplianceManager();

       // String searchCol = "Alias";
       // String pKeyColName = "ApplianceID";
       // String newValue = "Chubbier Tom";
        /*
        for (int i = 0; i < 100; i++) {
            //applianceManager.addAppliance(DATABASE_URL,TABLE_NAME_APPLIANCE_DATA, i, "Washer", "GE", "a3000HD", "Unit 10" + String.valueOf(i), "HOLD" , "Good", "09/05/2023", "Chubby Tom");
            String result = applianceManager.getCellValue(pKeyColName, searchCol, "");
            System.out.println(result);
            //manager.removeAppliance(i, pKeyColName);
            //manager.updateCellValue(searchCol, pKeyColName, i, newValue);
        }
        */
        //int nextKey = databaseManager.getNextPrimaryKey("ApplianceData", pKeyColName);
        //System.out.println(nextKey);

        // ADD IMAGE LINKS
        /*
            Refrigerator
            Washing Machine
            Oven
            Microwave
            Dishwasher
            Air Conditioner
            Furnace
        */
        /*
        String imageDataHomeDir ="/Users/matthewrivera/Projects/AMS/src/main/resources/Images/";
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Refrigerator", imageDataHomeDir + "refrigerator.png");
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Dishwasher", imageDataHomeDir + "dishwasher.png");
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Washing Machine", imageDataHomeDir + "washing-machine.png");
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Oven", imageDataHomeDir + "oven.png");
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Microwave", imageDataHomeDir + "microwave.png");
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Air Conditioner", imageDataHomeDir + "air-conditioner.png");
        applianceManager.addApplianceTypeImageLink(DATABASE_URL, TABLE_NAME_IMAGE_DATA, "Furnace", imageDataHomeDir + "furnace.png");

    }
    */
}
