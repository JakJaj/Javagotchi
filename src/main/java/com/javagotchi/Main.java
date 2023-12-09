package com.javagotchi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*; // pos only prob?


import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        Pane topSection = new Pane();
        topSection.setPrefSize(800, 150);
        topSection.setStyle("-fx-background-color: #FF0000;"); //red

        Pane bottomSection = new Pane();
        bottomSection.setPrefSize(800, 150);
        bottomSection.setStyle("-fx-background-color: #00FF00;"); //green

        Pane centerSection = new Pane();
        centerSection.setPrefSize(800, 300);
        centerSection.setStyle("-fx-background-color: #0000FF;"); //blue


        VBox root = new VBox(topSection, centerSection, bottomSection);
        scene.setRoot(root);

        Button button1 = new Button("Eat");
        Button button2 = new Button("Play");
        Button button3 = new Button("Clea");
        Button button4 = new Button("Sleep");

        button1.setPrefSize(150, 100);
        button2.setPrefSize(150, 100);
        button3.setPrefSize(150, 100);
        button4.setPrefSize(150, 100);

        HBox buttonContainer = new HBox(button1, button2, button3, button4);
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.CENTER);

        double totalButtonWidth = (button1.getPrefWidth() + button2.getPrefWidth() + button3.getPrefWidth() + button4.getPrefWidth()) + (buttonContainer.getSpacing() * 3); //padding calc
        double padding = (800 - totalButtonWidth) / 2;
        buttonContainer.setPadding(new Insets(0, padding, 0, padding));

        bottomSection.getChildren().add(buttonContainer);

        stage.setTitle("Welcome to Javagotchi!");
        stage.setScene(scene);
        stage.setResizable(false); //block window resizing from users
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}