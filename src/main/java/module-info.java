module com.example.javagotchi {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;


    opens com.javagotchi to javafx.fxml;
    exports com.javagotchi;
}