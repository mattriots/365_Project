package com.example._365_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.prefs.Preferences;

class MySQL {
    public static Connection setConnect() {
        Connection connect = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/workout?user=workout&password=gym123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static void signUp(Connection connect, String username, String password) throws SQLException{
        try{
            String userString = "insert into User values(?,?)";
            PreparedStatement updateUser = connect.prepareStatement(userString);
            updateUser.setString(1, username);
            updateUser.setString(2, password);
            updateUser.executeUpdate();
        }

        catch(SQLException e){
            while(e != null){
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }
    }


    public static boolean checkUser(Connection connect, String username) throws SQLException {
        try {
            String selectSQL = "SELECT username FROM User WHERE username = ? ";
            PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);

            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        }
        catch (SQLException e){
            while(e != null){
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }
        return false;
    }

    public static boolean login(Connection connect, String username, String password) throws SQLException {
        try {
            String selectSQL = "SELECT username, password FROM User WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();

        }
        catch (SQLException e){
            while(e != null){
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }
        return false;
    }

    public static boolean inputWorkout(Connection connect, String username, Date date, String exercise,
                                       String sets, String reps, String weight){
        try{
            String selectSQL = "insert into ExerciseLog (username, date, exercise, sets, reps, weight) " +
                    "values (?,?,?,?,?,?) ";
            PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, String.valueOf(date));
            preparedStatement.setString(3, exercise);
            preparedStatement.setString(4, sets);
            preparedStatement.setString(5, reps);
            preparedStatement.setString(6, weight);
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            while(e != null){
                System.err.println(e.getMessage());
                System.err.println(e.getSQLState());
                System.err.println(e.getErrorCode());
                e = e.getNextException();
            }
        }
        return false;
    }

//    public static void fillTable(Connection connect, Date date, ObservableList<ModelTable> list, String table_date,
//                                 String table_exercise, String table_sets, String table_reps, String table_weight){
//        try{
//            if(date == null) {
//                String selectSQL = "SELECT * FROM ExerciseLog WHERE username = ?";
//                PreparedStatement preparedStatement = connect.prepareStatement(selectSQL);
//                preparedStatement.setString(1, Preferences.userRoot().get("username", "workout"));
//                ResultSet rs = preparedStatement.executeQuery();
//                while (rs.next()) {
//                    list.add(new ModelTable(rs.getString("date"), rs.getString("exercise"), rs.getString("sets"),
//                            rs.getString("reps"), rs.getString("weight")));
//                }
//            }
//        else{
//            String newSelect = "SELECT * FROM ExerciseLog WHERE username = ? AND date = ?";
//            PreparedStatement newStatement = connect.prepareStatement(newSelect);
//            newStatement.setString(1, Preferences.userRoot().get("username", "workout"));
//            newStatement.setString(2, date.toString());
//            ResultSet newRs = newStatement.executeQuery();
//            while(newRs.next()){
//                list.add(new ModelTable(newRs.getString("date"), newRs.getString("exercise"), newRs.getString("sets"),
//                        newRs.getString("reps"), newRs.getString("weight")));
//            }
//        }
//    }
//        catch (SQLException e){
//            while(e != null){
//                System.err.println(e.getMessage());
//                System.err.println(e.getSQLState());
//                System.err.println(e.getErrorCode());
//                e = e.getNextException();
//            }
//        }
//
//    }

}