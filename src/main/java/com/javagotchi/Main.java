package com.javagotchi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.geometry.*; // pos only prob?
import java.io.IOException;
import java.io.InputStream;
import javafx.util.Duration;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        String containerBackground = "-fx-background-color: #44394d;";// it should go to fxml file
        Pane topSection = new Pane();
        topSection.setPrefSize(800, 150);
        topSection.setStyle(containerBackground);

        Pane bottomSection = new Pane();
        bottomSection.setPrefSize(800, 150);
        bottomSection.setStyle(containerBackground);

        Pane centerSection = new Pane();
        centerSection.setPrefSize(800, 300);

        // Load image as background
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("background.jpg");
        if (is == null) {
            System.out.println("Error: image not found");
            System.exit(1);
        }
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(is), // Image by pikisuperstar on Freepik "https://www.freepik.com/free-vector/pixel-art-mystical-background_29019077.htm#query=pixel%20art&position=0&from_view=keyword&track=ais&uuid=623d5b35-1c83-4891-bd52-b617b3a15dac"
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        centerSection.setBackground(new Background(backgroundImage));

        VBox root = new VBox(topSection, centerSection, bottomSection);
        scene.setRoot(root);

        Button button1 = new Button("EAT");
        Button button2 = new Button("PLAY");
        Button button3 = new Button("CLEAN");
        Button button4 = new Button("SLEEP");

        button1.setPrefSize(120, 60);
        button2.setPrefSize(120, 60);
        button3.setPrefSize(120, 60);
        button4.setPrefSize(120, 60);

        String buttonStyle = "-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"; // it should go to fxml file
        button1.setStyle(buttonStyle);
        button2.setStyle(buttonStyle);
        button3.setStyle(buttonStyle);
        button4.setStyle(buttonStyle);

        // Button press event handlers
        button1.setOnAction(e -> System.out.println("EAT"));
        button2.setOnAction(e -> System.out.println("PLAY"));
        button3.setOnAction(e -> System.out.println("CLEAN"));
        button4.setOnAction(e -> System.out.println("SLEEP"));

        HBox buttonContainer = new HBox(button1, button2, button3, button4);
        buttonContainer.setSpacing(30);
        buttonContainer.setAlignment(Pos.CENTER);

        double totalButtonWidth = (button1.getPrefWidth() + button2.getPrefWidth() + button3.getPrefWidth() + button4.getPrefWidth()) + (buttonContainer.getSpacing() * 3); // padding calc
        double padding = (800 - totalButtonWidth) / 2;
        buttonContainer.setPadding(new Insets(0, padding, 0, padding));
        buttonContainer.layoutYProperty().bind(bottomSection.heightProperty().subtract(buttonContainer.heightProperty()).divide(2));
        bottomSection.getChildren().add(buttonContainer);        

        stage.setTitle("Welcome to Javagotchi!");
        stage.setScene(scene);
        stage.setResizable(false); // Block window resizing from users
        stage.show();

        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            stage.setScene(scene); // Refreshing scene every 10 seconds (mainly for top bars)
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}