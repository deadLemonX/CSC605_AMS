package com.example.ams;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AMS_Controller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}