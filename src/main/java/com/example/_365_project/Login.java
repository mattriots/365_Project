package com.example._365_project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.prefs.*;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
    private Button logBtn;
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
        }

        else if(username.getText().isEmpty() || password.getText().isEmpty()){
            wrongLogIn.setText("Please enter your Username or Password.");
        }

        else if (!MySQL.checkUser(connect, username.getText())){
            wrongLogIn.setText("User does not exist. Please Sign Up");
        }
    }

    public void checkSignUp() throws SQLException, IOException {
        Connection connect = MySQL.setConnect();
        String hashPass;
        hashPass = hashPassword(signUpPass.getText());


        if(!Objects.equals(signUpPass.getText(), signUpConfirm.getText())) wrongSignUp.setText("Both Passwords must be the same");
        else if(signUpPass.getText().isEmpty() || signUpConfirm.getText().isEmpty() || signUpUser.getText().isEmpty()){
            wrongSignUp.setText("Please fill in all fields");
        }
        else if(MySQL.checkUser(connect, signUpUser.getText())){
            wrongSignUp.setText("Username already in use");
        }
        else {
            MySQL.signUp(connect, signUpUser.getText(), hashPass);
            wrongSignUp.setText("You have successfully Signed up. Please login");
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



}