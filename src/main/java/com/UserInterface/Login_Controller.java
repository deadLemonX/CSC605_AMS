package com.UserInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_Controller {
    @FXML private Button loginButton;
    @FXML private Button createAccountButton;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label wrongPass;
    private RegistrationController registrationController;

    public void onLoginButtonClick(ActionEvent actionEvent) {
        String enteredUsername = loginField.getText();
        String enteredPassword = passwordField.getText();

        // Check the database for the user's credentials
        boolean isUserValid = checkUserCredentials(enteredUsername, enteredPassword);

        if (isUserValid) {
            // Log the user in and navigate to the main menu
            // You can add code to open the main menu here

            // this closes the log in window so that we can go to the next part
            //Stage stage = (Stage) loginButton.getScene().getWindow();
            //stage.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ApplianceManagement.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Appliance Management");
                stage.setScene(new Scene(root));
                stage.showAndWait();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Display an error message for invalid login
            wrongPass.setText("Wrong Login Information");
        }

    }

    private boolean checkUserCredentials(String username, String password) {
        final String DATABASE_URL = "jdbc:sqlite:/Users/matthewrivera/Projects/AMS/AMS_Database";
        final String TABLE_NAME = "user_registration";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?")) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // True if a row matching the credentials is found, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle any database-related errors
        }
    }

    public void onCreateAccountButtonClick(ActionEvent actionEvent) {
        try {
            // Load the RegistrationDialog.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegistrationDialog.fxml"));
            Parent root = loader.load();
            // Create a new stage for the registration dialog
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registration");
            stage.setScene(new Scene(root));
            // Show the registration dialog
            stage.showAndWait();
            // Optionally, you can handle the registration logic here once the dialog is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
