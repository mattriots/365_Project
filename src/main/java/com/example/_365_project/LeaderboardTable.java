package com.example._365_project;

public class LeaderboardTable {
    String username;
    String exercise;
    String weight;

    public LeaderboardTable(String username, String exercise, String weight) {
        this.username = username;
        this.exercise = exercise;
        this.weight = weight;
    }

    public String getUsername() {
        return username;
    }

    public String getExercise() {
        return exercise;
    }

    public String getWeight() {
        return weight;
    }
}