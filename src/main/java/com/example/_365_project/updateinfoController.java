package com.example._365_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class updateinfoController {


        @FXML
        private TextField ageText;

        @FXML
        private Button submitButton;

        @FXML
        private TextField weightText;

        @FXML
        void submitButtonHandler(ActionEvent event) {
            Stage stage = (Stage)submitButton.getScene().getWindow();
            stage.close();
        }




}
