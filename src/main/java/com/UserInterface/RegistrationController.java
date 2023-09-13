package com.UserInterface;

import com.UserInterface.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistrationController {
    @FXML
    private Button createLoginButton;

    @FXML
    private TextField usernameRegistrationField;

    @FXML
    private PasswordField passwordRegistrationField;

    public void onCreateLoginButtonClick() {
        String username = usernameRegistrationField.getText();
        String password = passwordRegistrationField.getText();

        // Check if username or password is empty
        if (username.isEmpty() || password.isEmpty()) {
            // Display an error message or handle it as needed
            System.out.println("Username and password are required.");
            return;
        }

        final String DATABASE_URL = "jdbc:sqlite:registration_database.sqlite";
        final String TABLE_NAME = "user_registration";

        // Call the addUser method from registrationDatabaseStuff with the required arguments
        boolean registrationSuccess = UserData.addUser(DATABASE_URL, TABLE_NAME, username, password);

        if (registrationSuccess) {
            // Create and show a success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Registration Successful");
            successAlert.setHeaderText(null);
            successAlert.setContentText("User registered successfully!");
            successAlert.showAndWait();

            // Close the registration window
            Stage stage = (Stage) createLoginButton.getScene().getWindow();
            stage.close();
        } else {
            // Handle the registration failure, show an error message, etc.
            System.out.println("Registration failed. Please try again.");
        }
    }
}
