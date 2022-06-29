package com.example._365_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class workoutController implements Initializable{

    @FXML
    private ListView<WorkoutObject> workoutList;
    public static  List<WorkoutObject> workoutObjectList = new ArrayList<>();



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

    //Matt here:
    // Organizing and testing out the "Add Workout" Tab
    @FXML
    private ChoiceBox<String> workoutChoicebox;
    private String[] workout = {"Bench", "Squat", "Deadlift"};
    @FXML
    private DatePicker calendar;
    @FXML
    private TextField weightInput;
    @FXML
    private TextField repsInput;
    @FXML
    private TextField setsInput;

    // Currently will take in a work out and store it as a WorkoutObject in a list
    // Trying to figure out best way to integrate it into list view. Will currently show on "View/Edit" tab
    //  but needs some cleaning up
    @FXML
    void submitWorkoutHandler(ActionEvent event) {
        String workOutChoice = workoutChoicebox.getValue();
        String currentDate = String.valueOf(calendar.getValue());
        int numSets = Integer.parseInt(setsInput.getText());
        int numReps = Integer.parseInt(repsInput.getText());
        double weightAmt = Double.parseDouble(weightInput.getText());

        WorkoutObject currentWorkout = new WorkoutObject(currentDate, workOutChoice, numSets, numReps, weightAmt);
        workoutObjectList.add(currentWorkout);
        workoutList.getItems().add(currentWorkout);

        System.out.println("Added New Workout: ");
        System.out.println("Date: " + currentDate);
        System.out.println("Workout Type:" + workOutChoice);
        System.out.println("Sets: " + numSets);
        System.out.println("Reps: " + numReps);
        System.out.println("Weight: " + weightAmt);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workoutChoicebox.getItems().addAll(workout);
    }

    //Add/Edit Workout Tab

    @FXML
    private DatePicker calendarViewDelete;
    @FXML
    //Currently doesn't work
    private Button viewWorkouts;
    @FXML

    //Currently just outputs the workouts in a very ugly manner
    public void viewWorkoutHandler(ActionEvent actionEvent) {

        workoutList.getItems();
    }
}
