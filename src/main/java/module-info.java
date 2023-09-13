module com.example.ams {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ams to javafx.fxml;
    exports com.example.ams;
}