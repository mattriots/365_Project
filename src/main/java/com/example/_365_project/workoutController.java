package com.example._365_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class workoutController {

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private Button returnBtn;

    @FXML
    private TextArea textSum;

    @FXML
    private TextField username;

    @FXML
    void returnBtnHandler(ActionEvent event) {

    }

    @FXML
    void submitLoginHandler(ActionEvent event) {
        double num1 = Double.parseDouble(username.getText());
        double num2 = Double.parseDouble(password.getText());

        double res = num1 + num2;

        textSum.appendText(String.valueOf(res));
    }

}
