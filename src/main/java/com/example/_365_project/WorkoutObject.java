package com.example._365_project;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.util.Date;

public class WorkoutObject {

    private String date;
    private String workoutOption;
    private int numSets;
    private int numReps;
    private double weightAmt;



    WorkoutObject(String date,String workoutOption,  int numSets, int numReps, double weightAmt){
        this.date = date;
        this.workoutOption = workoutOption;
        this.numSets = numSets;
        this.numReps = numReps;
        this.weightAmt = weightAmt;


    }

    public String getWorkoutOption() {
        return workoutOption;
    }
    public String getDate() {
        return date;
    }

    public double getNumWeight() {
        return weightAmt;
    }

    public int getNumReps() {
        return numReps;
    }

    public int getNumSets() {
        return numSets;
    }

    @Override
    public String toString() {
        return date + " " + workoutOption + " " + numSets + " " + numReps + " " + weightAmt;
    }
}
