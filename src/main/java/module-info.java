module com.example.javagotchi {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires javafx.media;


    opens com.javagotchi to javafx.fxml;
    exports com.javagotchi;
}