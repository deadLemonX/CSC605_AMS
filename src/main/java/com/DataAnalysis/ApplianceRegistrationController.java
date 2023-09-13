package com.DataAnalysis;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ApplianceRegistrationController implements Initializable {
    ApplianceManager applianceManager;
    DatabaseManager databaseManager;
    @FXML private Button button_CancelWindow;
    @FXML private Button button_Registration;

    @FXML private ChoiceBox<String> choiceBox_ApplianceType;

    @FXML private TextField textFeild_Alias;
    @FXML private TextField textFeild_Location;
    @FXML private TextField textFeild_Model;
    @FXML private TextField textFeild_Brand;
    @FXML private ChoiceBox<String> choiceBox_DateDD;
    @FXML private ChoiceBox<String> choiceBox_DateMM;
    @FXML private ChoiceBox<String> choiceBox_DateYYYY;

    @FXML
    void handelButtonCancel(ActionEvent event) {
        Stage stage = (Stage) button_CancelWindow.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handelButtonRegistration(ActionEvent event) {
        final String DATABASE_URL = databaseManager.DATABASE_URL;
        final String TABLE_NAME_APPLIANCE_DATA = "ApplianceData";
        final String TABLE_NAME_IMAGE_DATA = "ApplianceImageTypeData";
        final String P_KEY_COL_NAME_APPLIANCE = "ApplianceID";
        final String P_KEY_COL_NAME_IMAGE = "Type";



        // pKey - ApplianceID
        int applianceID = databaseManager.getNextPrimaryKey(TABLE_NAME_APPLIANCE_DATA, P_KEY_COL_NAME_APPLIANCE);
        System.out.println(applianceID);
        String type = choiceBox_ApplianceType.getValue();
        String location = textFeild_Location.getText();
        String alias = textFeild_Alias.getText();
        String brand = textFeild_Model.getText();
        String model = textFeild_Brand.getText();
        String imageLink = (String) databaseManager.getCellValue(P_KEY_COL_NAME_IMAGE, "ImageLink", type, TABLE_NAME_IMAGE_DATA);

        int dateOfInstall_year = Integer.parseInt(choiceBox_DateYYYY.getValue());
        int dateOfInstall_month = Integer.parseInt(choiceBox_DateMM.getValue());
        int dateOfInstall_day = Integer.parseInt(choiceBox_DateDD.getValue());
        LocalDate inputDate = LocalDate.of(dateOfInstall_year, dateOfInstall_month, dateOfInstall_day);
        String dateOfInstall = dateOfInstall_month + "/" + dateOfInstall_day + "/" + dateOfInstall_year;
        String status = applianceManager.checkServiceStatus(inputDate);

        if (type != null && !location.isEmpty() && !dateOfInstall.isEmpty() && !alias.isEmpty() && !brand.isEmpty() && !model.isEmpty()) {
            Boolean bRegistratorAddDate = applianceManager.addAppliance(DATABASE_URL, TABLE_NAME_APPLIANCE_DATA, applianceID, type, brand, model, location, imageLink, status,  dateOfInstall,  alias);
            // function that return UI output of the status of data addition
            // FMXL unit to show out put
            Stage stage = (Stage) button_CancelWindow.getScene().getWindow();
            stage.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseManager = new DatabaseManager("jdbc:sqlite:/Users/matthewrivera/Projects/AMS/AMS_Database");
        applianceManager = new ApplianceManager();
        String[] arrApplianceTypes = { "Refrigerator", "Washing Machine", "Oven", "Microwave", "Dishwasher", "Air Conditioner", "Furnace"};
        choiceBox_ApplianceType.setItems(FXCollections.observableArrayList(arrApplianceTypes));

        String[] arrYears = {"1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023"};
        choiceBox_DateYYYY.setItems(FXCollections.observableArrayList(arrYears));
        String[] arrMonths = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        choiceBox_DateMM.setItems(FXCollections.observableArrayList(arrMonths));
        String[] arrDays = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        choiceBox_DateDD.setItems(FXCollections.observableArrayList(arrDays));
    }
}
