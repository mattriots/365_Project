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

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
    private void checkLogin() throws IOException, SQLException {
        Connection connect = MySQL.setConnect();
        Workout m = new Workout();
        if(MySQL.login(connect, username.getText(), password.getText())) {
            wrongLogIn.setText("Success!");

            m.changeScene("workOut.fxml");
        }

        else if (MySQL.login(connect, username.getText(), password.getText())){
            wrongLogIn.setText("Wrong username or password! Please try Again");
        }

        else if(username.getText().isEmpty() || password.getText().isEmpty()){
            wrongLogIn.setText("Please enter your Username or Password.");
        }

        else if (!MySQL.checkUser(connect, username.getText(), password.getText())){
            wrongLogIn.setText("User does not exist. Please Sign Up");
        }


    }

    public void checkSignUp() throws IOException{
        if(signUpPass != signUpConfirm) wrongSignUp.setText("Both Passwords must be the same");
        else wrongSignUp.setText("Successfully Signed up!");
    }


    public void submitLoginHandler(ActionEvent actionEvent) throws IOException, SQLException {
        checkLogin();
    }

    public void submitSignUpHandler(ActionEvent actionEvent) throws IOException {
        checkSignUp();
    }
}