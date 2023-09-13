module com.example.notificationsettingtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires twilio;
    requires javax.mail.api;
    requires org.jetbrains.annotations;
    requires java.xml.bind;


    opens com.notificationsetting to javafx.fxml;
    exports com.notificationsetting;
}