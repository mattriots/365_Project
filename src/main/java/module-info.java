module com.example.finalproject365 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.java;
    requires java.prefs;

    opens com.example._365_project to javafx.fxml;
    exports com.example._365_project;
}