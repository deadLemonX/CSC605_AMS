package com.notificationsetting;

import com.notificationsetting.NotificationSetting;
import com.twilio.Twilio;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.notificationsetting.NotificationSetting.*;

/*
This is to test the code
 */
public class NotificationSettingUI extends Application {

    // Your Twilio and other constants here
    private static final String ACCOUNT_SID = "AC8e1f927b80c8ed7e7d7b40e2a0257be8";
    private static final String AUTH_TOKEN = "3c0e554c1f6db0579c473cfd21656922";
    private static final String PHONE_NUMBER = "18557751147";
    private NotificationSetting notificationSetting;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Notification Setting");

        notificationSetting = new NotificationSetting();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 0);

        // Add a label for displaying email status
        Label statusLabel = new Label();
        GridPane.setConstraints(statusLabel, 1, 5);

        Label phoneLabel = new Label("Phone Number:");
        GridPane.setConstraints(phoneLabel, 0, 1);
        TextField phoneInput = new TextField();
        GridPane.setConstraints(phoneInput, 1, 1);

        Label applianceLabel = new Label("Appliance:");
        GridPane.setConstraints(applianceLabel, 0, 2);
        TextField applianceInput = new TextField();
        GridPane.setConstraints(applianceInput, 1, 2);

        Label locationLabel = new Label("Location:");
        GridPane.setConstraints(locationLabel, 0, 3);
        TextField locationInput = new TextField();
        GridPane.setConstraints(locationInput, 1, 3);

        Button sendButton = new Button("Send Notification");
        GridPane.setConstraints(sendButton, 1, 4);

        sendButton.setOnAction(e -> {
            // Get user input
            String userEmail = emailInput.getText();
            String userPhoneNumber = phoneInput.getText();
            String userDevices = applianceInput.getText();
            String userDevicesLocation = locationInput.getText();

            // Initialize Twilio
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            // Simulate random status
            boolean userDevicesStatus = getRandomStatus();

            // Monitor appliance status
            String applianceStatus = monitorApplianceStatus(userDevices, userDevicesLocation, userDevicesStatus);

            // Encrypt the message
            String encryptedMessage = encryptionMessage(applianceStatus, "AMS4everYAY");

            notificationSetting = new NotificationSetting();

            //Notify User about appliances not working
            if (!userDevicesStatus) {
                alertMessage(userDevices, userDevicesStatus, userDevicesLocation);
                sendEncryptedEmail(userEmail, encryptedMessage, userDevicesStatus);
                sendEncryptedSMS(userPhoneNumber, "Appliance Status Alert", encryptedMessage, userDevicesStatus);
                statusLabel.setText("Email/SMS sent successfully!");
            } else {
                statusLabel.setText("Email/SMS was not sent.\nAppliance is in good condition");
            }

        });

        grid.getChildren().addAll(emailLabel, emailInput, statusLabel, phoneLabel, phoneInput, applianceLabel,
                applianceInput, locationLabel, locationInput, sendButton);

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}