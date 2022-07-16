package com.example._365_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import static com.example._365_project.workoutController.connect;

public class updateinfoController {


        @FXML
        private TextField ageUpdateBox;

        @FXML
        private Button submitButton;

        @FXML
        private TextField weightUpdateBox;

        @FXML
        void submitButtonHandler(ActionEvent event) {
            Stage stage = (Stage)submitButton.getScene().getWindow();
            stage.close();
        }

        @FXML
        void updateBtnHandler(ActionEvent event) {
                try {

                        // if only age updated
                        if((weightUpdateBox.getText().isBlank() || weightUpdateBox.getText().isBlank())
                                && isInteger(ageUpdateBox.getText())){
                                String selectWeight = "UPDATE UserInfo SET age = ? WHERE username = ?";
                                PreparedStatement updateStats = connect.prepareStatement(selectWeight);
                                updateStats.setString(1, ageUpdateBox.getText());
                                updateStats.setString(2, Preferences.userRoot().get("username", "profile"));
                                updateStats.executeUpdate();

//                                ageStatLabel.setText(ageUpdateBox.getText());
                        }

                        // if only weight updated
                        if((ageUpdateBox.getText().isBlank() || ageUpdateBox.getText().isBlank())
                                && isInteger(weightUpdateBox.getText())){
                                String selectWeight2 = "UPDATE UserInfo SET weight = ? WHERE username = ?";
                                PreparedStatement updateStats2 = connect.prepareStatement(selectWeight2);
                                updateStats2.setString(1, weightUpdateBox.getText());
                                updateStats2.setString(2, Preferences.userRoot().get("username", "profile"));
                                updateStats2.executeUpdate();


//                                workoutController.updateWeightLabel(weightUpdateBox.getText());
//                                weightStatLabel.setText(weightUpdateBox.getText());
                        }

                        // if both age and weight updated
                        if(isInteger(ageUpdateBox.getText()) && isInteger(weightUpdateBox.getText())) {

                                String selectWeight = "UPDATE UserInfo SET age = ?, weight = ? WHERE username = ?";
                                PreparedStatement updateStats = connect.prepareStatement(selectWeight);
                                updateStats.setString(1, ageUpdateBox.getText());
                                updateStats.setString(2, weightUpdateBox.getText());
                                updateStats.setString(3, Preferences.userRoot().get("username", "profile"));
                                updateStats.executeUpdate();

//                                workoutController.updateAgeLabel(ageUpdateBox.getText());
//                                workoutController.updateWeightLabel(weightUpdateBox.getText());
//                                ageStatLabel.setText(ageUpdateBox.getText());
//                                weightStatLabel.setText(weightUpdateBox.getText());
                        }
                        ageUpdateBox.setText("");
                        weightUpdateBox.setText("");
                }
                catch (SQLException e){
                        while(e != null){
                                System.err.println(e.getMessage());
                                System.err.println(e.getSQLState());
                                System.err.println(e.getErrorCode());
                                e = e.getNextException();
                        }
                }
        }

        public static boolean isInteger(String strNum) {
                if (strNum == null) {
                        return false;
                }
                try {
                        double i = Integer.parseInt(strNum);
                } catch (NumberFormatException nfe) {
                        return false;
                }
                return true;
        }




}
