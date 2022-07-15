package com.example._365_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Workout extends Application {
    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("login.fxml"))));
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Scene scene = new Scene(root);
        stage.setTitle("Workout Tracker");
        stage.setScene(scene);
        stage.show();
        // added some changes
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stg.getScene().setRoot(pane);
    }



    public static void main(String[] args) {
        launch();
    }
}