package com.example.ams_scheduler;

import com.example.ams.Scheduler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SchedulerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulerApplication.class.getResource("Scheduler_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1020, 800);
        stage.setTitle("Appliance Maintenance Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        //set up database parameters
        final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\MikeJ\\IdeaProjects\\AMS_Scheduler\\AMS_Scheduler";

        final String SCHEDULE_TABLE_NAME = "ScheduleTable";
        final String MAINTENANCE_TABLE_NAME = "MaintenanceTable";
        final String TEST_APPLIANCE_TABLE_NAME = "ApplianceTestTable";

        //delete previous tables to build new ones
        Scheduler.clearScheduleTable(DATABASE_URL, SCHEDULE_TABLE_NAME);
        MaintenancePersonnel.clearMaintenanceTable(DATABASE_URL, MAINTENANCE_TABLE_NAME);

        //build new maintenance table
        for (int i = 1; i <= 20; i++)
        {
            boolean buildMaintanceTable = MaintenancePersonnel.addWorker(DATABASE_URL, MAINTENANCE_TABLE_NAME, i, "Sancho" + i, true);
        }

        launch();
        /*
        * NOTES
        * I added 2 function to the data manager that we need
        * The IDE suggested I move the Scheduler class into a different folder. I don't know why. I hope it doesn't cause issues.
        *
        * All calls to the APPLIANCE_MANAGER_TEST table need to be replaced with the real table
        * database URL probably needs to be changed
        *
        * Currently when you fetch appliances that need repair in the dropdown/combobox, they only show the appliance ID.
        * You can see the appliance type when you add them to the schedule. I'd like to add more detail to the combo box
        *
        * Maintenance table and Scheduler table are destroyed and rebuilt everytime you run
        *
        * To use The scheduler click the button at the top to get appliances that need repair
        * This populates an appliance dropdown.
        * Select an appliance, a technician, and a date
        * Press confirm to add to the table and display in the text area. Adding duplicates or incomplete submissions should fail
        *
        * */

    }
}