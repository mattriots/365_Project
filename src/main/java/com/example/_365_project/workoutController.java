package com.example._365_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class workoutController implements Initializable {

    public static Connection connect = MySQL.setConnect();
    public Menu leader_overall;
    public MenuItem leader_overall_week;
    public MenuItem leader_overall_month;
    public MenuItem leader_overall_year;
    public MenuItem leader_squat_week;
    public MenuItem leader_squat_month;
    public MenuItem leader_squat_year;
    public MenuItem leader_bench_week;
    public MenuItem leader_bench_month;
    public MenuItem leader_bench_year;
    public MenuItem leader_dead_week;
    public MenuItem leader_dead_month;
    public MenuItem leader_dead_year;
    public MenuItem leader_cal_week;
    public MenuItem leader_cal_month;
    public MenuItem leader_cal_year;

    @FXML
    private TableView<ModelTable> exerciseTable;

    @FXML
    private TableView<LeaderboardTable> leaderboardTable;

    @FXML
    private TableColumn<LeaderboardTable, String> username_col;

    @FXML
    private TableColumn<LeaderboardTable, String> weight_col;

    @FXML
    private TableColumn<LeaderboardTable, String> exercise_col;

    @FXML
    private TableColumn<ModelTable, String> table_date;

    ObservableList<LeaderboardTable> leaderboardList = FXCollections.observableArrayList();

    @FXML
    private DatePicker table_Datepicker;

    @FXML
    private TableColumn<ModelTable, String> table_exercise;

    @FXML
    private TableColumn<ModelTable, String> table_reps;

    @FXML
    private TableColumn<ModelTable, String> table_sets;

    @FXML
    private TableColumn<ModelTable, String> table_weight;

    ObservableList<ModelTable> list = FXCollections.observableArrayList();

    @FXML
    private Button addWorkout;

    @FXML
    private Label profileText;

    @FXML
    private DatePicker datepicker;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private Button returnBtn;


    @FXML
    private Button tableBtn;

    @FXML
    private TextField username;

    @FXML
    private TextField setsBox;

    @FXML
    private TextField repsBox;

    @FXML
    private Label exerciseLabel;
    @FXML
    private TextField weightBox;

    @FXML
    private ChoiceBox<String> workoutChoicebox;

    @FXML
    private ChoiceBox<String> chartChoicebox;

    @FXML
    private Button chartButton;

    private String[] workout = {"Bench Press", "Squat", "Deadlift", "Dumbell Curl", "Barbell Curl",
            "Push-ups", "Pull-ups"};


    @FXML
    void workoutChoiceHandler(MouseEvent event) {


    }

    @FXML
    void returnBtnHandler(ActionEvent event) {

    }

    @FXML
    void submitWorkoutHandler(ActionEvent event) {

        java.sql.Date getDate = java.sql.Date.valueOf(datepicker.getValue());
        if (MySQL.inputWorkout(connect, Preferences.userRoot().get("username", "workout"), getDate,
                workoutChoicebox.getSelectionModel().getSelectedItem(), setsBox.getText(),
                repsBox.getText(), weightBox.getText())) {
            exerciseLabel.setText("Your exercise has been logged");
        } else exerciseLabel.setText("Please try again with valid data");
    }

    @FXML
    void submitChartHandler(ActionEvent event) {
        lineChart.getData().removeAll();
        lineChart.getData().clear();

        XYChart.Series series = new XYChart.Series();
        series.setName("Weight");
        try {
            String selectWeight = "SELECT date, weight FROM ExerciseLog WHERE username = ? AND exercise = ?";
            PreparedStatement ps = connect.prepareStatement(selectWeight);
            ps.setString(1, Preferences.userRoot().get("username", "workout"));
            ps.setString(2, chartChoicebox.getSelectionModel().getSelectedItem());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                series.getData().add(new XYChart.Data(rs.getString("date"), Double.parseDouble(rs.getString("weight"))));
            }
            lineChart.getData().add(series);
        } catch (SQLException e) {
            while (e != null) {
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }

    }

    @FXML
    void submitTableHandler(ActionEvent event) {

        try {
            if (table_Datepicker.getValue() == null) {
                String selectSQL = "SELECT * FROM ExerciseLog WHERE username = ?";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                preparedStatement.setString(1, Preferences.userRoot().get("username", "workout"));
                ResultSet rs = preparedStatement.executeQuery();
                list.removeAll(list);
                while (rs.next()) {
                    list.add(new ModelTable(rs.getString("date"), rs.getString("exercise"), rs.getString("sets"),
                            rs.getString("reps"), rs.getString("weight")));
                }
            } else {
                String newSelect = "SELECT * FROM ExerciseLog WHERE username = ? AND date = ?";
                PreparedStatement newStatement = connect.prepareStatement(newSelect);
                newStatement.setString(1, Preferences.userRoot().get("username", "workout"));
                newStatement.setString(2, String.valueOf(table_Datepicker.getValue()));
                ResultSet newRs = newStatement.executeQuery();
                list.removeAll(list);
                while (newRs.next()) {
                    list.add(new ModelTable(newRs.getString("date"), newRs.getString("exercise"), newRs.getString("sets"),
                            newRs.getString("reps"), newRs.getString("weight")));
                }
            }
        } catch (SQLException e) {
            while (e != null) {
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }
        table_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table_exercise.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        table_sets.setCellValueFactory(new PropertyValueFactory<>("sets"));
        table_reps.setCellValueFactory(new PropertyValueFactory<>("reps"));
        table_weight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        exerciseTable.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profileText.setText("Profile: " + Preferences.userRoot().get("username", "profile"));
        workoutChoicebox.getItems().addAll(workout);
        chartChoicebox.getItems().addAll(workout);

        try {
            String selectSQL = "SELECT username, exercise, weight FROM ExerciseLog ORDER BY exercise ASC, weight DESC";
            PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            leaderboardList.removeAll(leaderboardList);

            while (rs.next()) {
                leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                        rs.getString("exercise"), rs.getString("weight")));
            }
        } catch (SQLException e) {
            while (e != null) {
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }
        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        exercise_col.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));

        leaderboardTable.setItems(leaderboardList);

    }


    public void leaderOverallHandler(ActionEvent actionEvent) {

        //Overall by week
        leader_overall_week.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, AVG(calories) as calAvg" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 7" +
                        "    GROUP by username, date" +
                        "    ORDER by calAvg desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calAvg")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by month
        leader_overall_month.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, AVG(calories) as calAvg" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 30" +
                        "    GROUP by username, date" +
                        "    ORDER by calAvg desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calAvg")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by Year
        leader_overall_year.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, AVG(calories) as calAvg" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 365" +
                        "    GROUP by username, date" +
                        "    ORDER by calAvg desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calAvg")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });

        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        exercise_col.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));

        leaderboardTable.setItems(leaderboardList);
    }

    public void leaderSquatHandler(ActionEvent actionEvent) {

        leader_squat_week.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 7 AND exercise = 'Squat'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by month
        leader_squat_month.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 30 AND exercise = 'Squat'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by Year
        leader_squat_year.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 365 AND exercise = 'Squat'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });

        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        exercise_col.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));

        leaderboardTable.setItems(leaderboardList);
    }

    public void leaderBenchHandler(ActionEvent actionEvent) {

        leader_bench_week.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 7 AND exercise = 'Bench Press'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by month
        leader_bench_month.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 30 AND exercise = 'Bench Press'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by Year
        leader_bench_year.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 365 AND exercise = 'Bench Press'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });

        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        exercise_col.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));

        leaderboardTable.setItems(leaderboardList);
    }

    public void leaderDeadHandler(ActionEvent actionEvent) {

        leader_dead_week.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 7 AND exercise = 'Deadlift'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by month
        leader_dead_month.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 30 AND exercise = 'Deadlift'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by Year
        leader_dead_year.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 365 AND exercise = 'Deadlift'" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });

        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        exercise_col.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));

        leaderboardTable.setItems(leaderboardList);
    }

    public void leaderCalHandler(ActionEvent actionEvent) {

        leader_cal_week.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 7" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by month
        leader_cal_month.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 30" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });
        //Overall by Year
        leader_cal_year.setOnAction(event -> {
            try {
                String selectSQL = "SELECT username, date, sum(calories) as calSum" +
                        "    FROM ExerciseLog" +
                        "    WHERE datediff(date, current_date()) <= 365" +
                        "    GROUP by username, date" +
                        "    ORDER by calSum desc;";
                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
                ResultSet rs = preparedStatement.executeQuery();
                leaderboardList.removeAll(leaderboardList);

                while (rs.next()) {
                    leaderboardList.add(new LeaderboardTable(rs.getString("username"),
                            rs.getString("date"), rs.getString("calSum")));
                }
            } catch (SQLException e) {
                while (e != null) {
                    System.err.println(e.getMessage());
                    System.err.println(e.getSQLState());
                    System.err.println(e.getErrorCode());
                    e = e.getNextException();

                }
            }
        });

        username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        exercise_col.setCellValueFactory(new PropertyValueFactory<>("exercise"));
        weight_col.setCellValueFactory(new PropertyValueFactory<>("weight"));

        leaderboardTable.setItems(leaderboardList);
    }
}