

module com.example.ams {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.sql;
        requires twilio;
        //requires javax.mail.api;
        //requires org.jetbrains.annotations;
        //requires java.xml.bind;

        opens com.UserInterface to javafx.fxml;
        exports com.UserInterface;
        opens com.DataAnalysis to javafx.fxml;
        exports com.DataAnalysis;
        //opens com.notificationsetting to javafx.fxml;
        //exports com.notificationsetting;
}