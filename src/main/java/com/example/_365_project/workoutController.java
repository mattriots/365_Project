package com.example._365_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class workoutController{

    @FXML
    private Button addWorkout;

    @FXML
    private DatePicker datepicker;

    private Label setsLabel;
    private Label repsLabel;
    private Label weightLabel;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private Button returnBtn;

    @FXML
    private TextArea textSum;

    @FXML
    private TextArea exerciseText;

    @FXML
    private TextField username;

    @FXML
    private TextField setsBox;

    @FXML
    private TextField repsBox;

    @FXML
    private TextField weightBox;

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





}
