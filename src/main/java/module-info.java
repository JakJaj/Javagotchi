module com.example.javagotchi {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens com.javagotchi to javafx.fxml;
    exports com.javagotchi;
}