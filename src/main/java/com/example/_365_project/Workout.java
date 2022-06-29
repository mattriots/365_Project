package com.example._365_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Workout extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("workOut.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        // added some changes
    }

    public static void main(String[] args) {
        launch();
    }
}