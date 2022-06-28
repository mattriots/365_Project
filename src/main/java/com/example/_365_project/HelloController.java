package com.example._365_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

}