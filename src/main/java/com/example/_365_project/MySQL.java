package com.example._365_project;

import java.net.CookieHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import com.mysql.jdbc.Driver;

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
            String hashPass = "UPDATE User SET password = SHA(?) WHERE username = ?";
            PreparedStatement updatePass = connect.prepareStatement(hashPass);
            updatePass.executeUpdate();
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

}