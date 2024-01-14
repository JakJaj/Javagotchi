module com.example.javagotchi {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires lombok;
    requires java.sql;


    opens com.javagotchi to javafx.fxml;
    exports com.javagotchi;
}