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
    private void checkLogin() throws IOException, SQLException {
        Connection connect = MySQL.setConnect();
        Workout m = new Workout();

        if(MySQL.login(connect, username.getText(), password.getText())) {
            wrongLogIn.setText("Success!");
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

    public void checkSignUp() throws SQLException {
        Connection connect = MySQL.setConnect();
        if(!Objects.equals(signUpPass.getText(), signUpConfirm.getText())) wrongSignUp.setText("Both Passwords must be the same");
        else if(signUpPass.getText().isEmpty() || signUpConfirm.getText().isEmpty() || signUpUser.getText().isEmpty()){
            wrongSignUp.setText("Please fill in all fields");
        }
        else if(!MySQL.checkUser(connect, username.getText())){
            wrongSignUp.setText("Username already in use, please try a different one");
        }
        else {
            MySQL.signUp(connect, username.getText(), password.getText());
            wrongSignUp.setText("You have successfully Signed up. Please login");
        }
    }


    public void submitLoginHandler(ActionEvent actionEvent) throws IOException, SQLException {
        checkLogin();
    }

    public void submitSignUpHandler(ActionEvent actionEvent) throws SQLException {
        checkSignUp();
    }
}