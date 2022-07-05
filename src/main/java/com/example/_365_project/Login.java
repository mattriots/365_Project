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
    private void checkLogin() throws IOException {
        Workout m = new Workout();
        if(username.getText().equals("workout") && password.getText().equals("123")) {
            wrongLogIn.setText("Success!");

            m.changeScene("workOut.fxml");
        }

        else if(username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogIn.setText("Please enter your data.");
        }


        else {
            wrongLogIn.setText("Wrong username or password!");
        }
    }

    public void checkSignUp() throws IOException{
        if(signUpPass != signUpConfirm) wrongSignUp.setText("Both Passwords must be the same");
        else wrongSignUp.setText("Successfully Signed up!");
    }


    public void submitLoginHandler(ActionEvent actionEvent) throws IOException {
        checkLogin();
    }

    public void submitSignUpHandler(ActionEvent actionEvent) throws IOException {
        checkSignUp();
    }
}