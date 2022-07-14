package com.example._365_project;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;


public class Login {


    public Login() {
    }

    @FXML
    private Label wrongSignUp;

    @FXML
    private PasswordField signUpConfirm;

    @FXML
    private PasswordField signUpPass;

    @FXML
    private TextField signUpUser;

    @FXML
    private Button signupBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;


    @FXML
    private void checkLogin() throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        Connection connect = MySQL.setConnect();
        Workout m = new Workout();
        String hashPass;
        hashPass = hashPassword(password.getText());

        if(MySQL.login(connect, username.getText(), hashPass)) {
            wrongLogIn.setText("Success!");
            Preferences p = Preferences.userRoot();
            String profileUsername = username.getText();
            p.put("username", profileUsername);
            m.changeScene("workOut.fxml");
        }

        else if (!MySQL.login(connect, username.getText(), password.getText())){
            wrongLogIn.setText("Wrong username or password! Please try Again");
            clearLabel(wrongLogIn, 3000);
        }

        else if(username.getText().isEmpty() || password.getText().isEmpty()){
            wrongLogIn.setText("Please enter your Username or Password.");
            clearLabel(wrongLogIn, 3000);
        }

        else if (!MySQL.checkUser(connect, username.getText())){
            wrongLogIn.setText("User does not exist. Please Sign Up");
            clearLabel(wrongLogIn, 3000);
        }
    }

    public void checkSignUp() throws SQLException, IOException {
        Connection connect = MySQL.setConnect();
        String hashPass;
        hashPass = hashPassword(signUpPass.getText());


        if(!Objects.equals(signUpPass.getText(), signUpConfirm.getText())) wrongSignUp.setText("Both Passwords must be the same");
        else if(signUpPass.getText().isEmpty() || signUpConfirm.getText().isEmpty() || signUpUser.getText().isEmpty()){
            wrongSignUp.setText("Please fill in all fields");
            clearLabel(wrongSignUp, 3000);
        }
        else if(MySQL.checkUser(connect, signUpUser.getText())){
            wrongSignUp.setText("Username already in use");
            clearLabel(wrongSignUp, 3000);
        }
        else {
            MySQL.signUp(connect, signUpUser.getText(), hashPass);
            wrongSignUp.setText("You have successfully Signed up. Please login");
            clearLabel(wrongSignUp, 5000);
        }
    }

    public static String hashPassword(String password) throws IOException{
        try
        {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            // Get complete hashed password in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void submitLoginHandler(ActionEvent actionEvent) throws IOException, SQLException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        checkLogin();

    }

    public void submitSignUpHandler(ActionEvent actionEvent) throws SQLException, IOException {
        checkSignUp();
    }

    public void logInMousePressedHandler(MouseEvent actionEvent) {
        loginBtn.setLayoutX(loginBtn.getLayoutX() + 3);
        loginBtn.setLayoutY(loginBtn.getLayoutY() + 3);
    }

    public void logInMouseReleasedHandler(MouseEvent actionEvent) {
        loginBtn.setLayoutX(loginBtn.getLayoutX() - 3);
        loginBtn.setLayoutY(loginBtn.getLayoutY() - 3);
    }

    public void signUpMousePressedHandler(MouseEvent actionEvent) {
        signupBtn.setLayoutX(signupBtn.getLayoutX() + 2);
        signupBtn.setLayoutY(signupBtn.getLayoutY() + 3);
    }

    public void signUpMouseReleasedHandler(MouseEvent actionEvent) {
        signupBtn.setLayoutX(signupBtn.getLayoutX() - 2);
        signupBtn.setLayoutY(signupBtn.getLayoutY() - 3);
    }


    private static void clearLabel(Label label, int milliseconds) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(milliseconds);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };

        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                if(!label.getText().equals(""))
                    label.setText("");
            }
        });

        new Thread(sleeper).start();
    }

}