package com.example._365_project;

public class ModelTable {
    String date;
    String exercise;
    String sets;
    String reps;
    String weight;

    public ModelTable(String date, String exercise, String sets, String reps, String weight){
        this.date = date;
        this.exercise = exercise;
        this.sets= sets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public String getExercise() {
        return exercise;
    }

    public String getReps() {
        return reps;
    }

    public String getSets() {
        return sets;
    }

    public String getWeight() {
        return weight;
    }
}