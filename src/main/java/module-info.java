module com.nabnab.reservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;


    opens com.nabnab.reservation to javafx.fxml;
    exports com.nabnab.reservation;
}