package com.example.ams_scheduler;

import com.example.ams.Scheduler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SchedulerController {
    @FXML
    private DatePicker MaintenanceDatePicker;

    @FXML
    private Label CalendarLabel;

    @FXML
    private Button GetRepairButton;

    @FXML
    private ComboBox SelectApplianceComboBox;

    @FXML
    private Label AlertLabel;

    @FXML
    private ComboBox SelectTechnicianComboBox;

    @FXML
    private TextArea AppointmentsTextArea;

    @FXML
    private Button ConfirmAppointmentButton;

    @FXML
    private Label AppointmentConfirmationLabel;


    final String SCHEDULE_TABLE_NAME = "ScheduleTable";
    final String MAINTENANCE_TABLE_NAME = "MaintenanceTable";
    final String TEST_APPLIANCE_TABLE_NAME = "ApplianceTestTable";

    final String APPLIANCE_KEY_COLUMN_NAME = "ApplianceID";

    final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\MikeJ\\IdeaProjects\\AMS_Scheduler\\AMS_Scheduler";
    List<Integer> applianceKeys = new ArrayList<Integer>();
    List<String> techNames = new ArrayList<String>();



    DatabaseManager databaseManager = new DatabaseManager<>(DATABASE_URL);


    // gets appliance IDs flaged as "Needs Repair"
    // populates them in a combo box
    // adds all technicians from MaintenanceTable to another combo box for selection
    @FXML
    public void onGetRepairButtonClick(ActionEvent event){
        applianceKeys.clear();

        List<Integer> allPrimaryKeys = databaseManager.getAllPrimaryKeys(TEST_APPLIANCE_TABLE_NAME, "ApplianceID");

        for (int primaryKey : allPrimaryKeys) {
            String status = String.valueOf(databaseManager.getCellValue("ApplianceID", "Status", primaryKey, TEST_APPLIANCE_TABLE_NAME));
            if ("Needs Service".equals(status)) {
                applianceKeys.add(primaryKey);
            }
        }
        SelectApplianceComboBox.getItems().clear();
        SelectApplianceComboBox.getItems().addAll(applianceKeys);



        techNames.clear();

        List<Integer> allPrimaryKeysM = databaseManager.getAllPrimaryKeys(MAINTENANCE_TABLE_NAME, "MaintenanceID");

        for (int primaryKey : allPrimaryKeysM) {
            String name = String.valueOf(databaseManager.getCellValue("MaintenanceID", "WorkerName", primaryKey, MAINTENANCE_TABLE_NAME));
            techNames.add(name);

        }
        SelectTechnicianComboBox.getItems().clear();
        SelectTechnicianComboBox.getItems().addAll(techNames);

    }


// adds selections to the Schedule database table, and outputs the table to a text area in the GUI
    @FXML
    private void onConfirmAppointmentButtonClick(ActionEvent event) {

        //clear message label
        AppointmentConfirmationLabel.setText("");
        //get data
        int selectedAppliance = Integer.valueOf(String.valueOf(SelectApplianceComboBox.getSelectionModel().getSelectedItem()));
        String selectedTechnician = String.valueOf(SelectTechnicianComboBox.getSelectionModel().getSelectedItem());
        LocalDate selectedDate = MaintenanceDatePicker.getValue();

        if (selectedAppliance > 0 && selectedTechnician != null && selectedDate != null) {
            // All selections are made
            String appType = String.valueOf(databaseManager.getCellValue(APPLIANCE_KEY_COLUMN_NAME, "Type", selectedAppliance, TEST_APPLIANCE_TABLE_NAME));
            String appStatus = String.valueOf(databaseManager.getCellValue(APPLIANCE_KEY_COLUMN_NAME, "Status", selectedAppliance, TEST_APPLIANCE_TABLE_NAME));

            //add new appointment to the schedule table
            Scheduler.addAppointment(DATABASE_URL, SCHEDULE_TABLE_NAME, selectedAppliance, appType, appStatus, Date.valueOf(selectedDate), selectedTechnician);

            //List to fill with schedule table for output
            List<String> allAppointments = databaseManager.getAllAppointments(SCHEDULE_TABLE_NAME);

            StringBuilder outputText = new StringBuilder();
            for (String appointment : allAppointments) {
                outputText.append(appointment).append("\n");
            }

            AppointmentsTextArea.setText(outputText.toString());
            AppointmentConfirmationLabel.setText("Appointment added!");
        } else {
            // One or more selections are missing
            AppointmentConfirmationLabel.setText("Please make sure all selections are made before proceeding.");
        }
    }
}