package com.example._365_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class workoutController implements Initializable{

    @FXML
    private Button addWorkout;

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
    private ChoiceBox<String> workoutChoicebox;
    private String[] workout = {"Bench Press", "Squat", "Deadlift", "Dumbell Curl", "Barbell Curl",
    "Push-ups", "Pull-ups"};


    @FXML
    void workoutChoiceHandler(MouseEvent event) {

    }

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

    @FXML
    void submitWorkoutHandler(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        workoutChoicebox.getItems().addAll(workout);
    }

}
