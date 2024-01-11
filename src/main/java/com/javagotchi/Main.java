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
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;


public class Main extends Application {
    private Character character;
    private Label nameLabel;
    private Label ageLabel;
    private Label healthLabel;
    private Label hungerLabel;
    private Label cleanlinessLabel;
    private Label happinessLabel;
    private Label energyLabel;

    @Override
    public void start(Stage stage) throws IOException {
        character = Character.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        String containerBackground = "-fx-background-color: #44394d;";// it should go to fxml file
        Pane topSection = new Pane();
        topSection.setPrefSize(800, 150);
        topSection.setStyle(containerBackground);

        // Test labels
        nameLabel = new Label("Energy: " + character.getEnergy());
        ageLabel = new Label("Age: " + character.getAge());
        healthLabel = new Label("Health: " + character.getHealth());
        hungerLabel = new Label("Hunger: " + character.getHunger());
        cleanlinessLabel = new Label("Cleanliness: " + character.getCleanliness());
        happinessLabel = new Label("Happiness: " + character.getHappiness());
        energyLabel = new Label("Energy: " + character.getEnergy());

        String labelStyle = "-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';";
        nameLabel.setLayoutX(20);
        nameLabel.setLayoutY(20);
        nameLabel.setStyle(labelStyle);
        ageLabel.setLayoutX(20);
        ageLabel.setLayoutY(50);
        ageLabel.setStyle(labelStyle);
        healthLabel.setLayoutX(20);
        healthLabel.setLayoutY(80);
        healthLabel.setStyle(labelStyle);
        hungerLabel.setLayoutX(20);
        hungerLabel.setLayoutY(110);
        hungerLabel.setStyle(labelStyle);
        cleanlinessLabel.setLayoutX(150);
        cleanlinessLabel.setLayoutY(20);
        cleanlinessLabel.setStyle(labelStyle);
        happinessLabel.setLayoutX(150);
        happinessLabel.setLayoutY(50);
        happinessLabel.setStyle(labelStyle);
        energyLabel.setLayoutX(150);
        energyLabel.setLayoutY(80);
        energyLabel.setStyle(labelStyle);

        topSection.getChildren().addAll(nameLabel, ageLabel, healthLabel, hungerLabel, cleanlinessLabel, happinessLabel, energyLabel);
        // Top section needs some cleaning up

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

        Button buttonEat = new Button("EAT");
        Button buttonPlay = new Button("PLAY");
        Button buttonClean = new Button("CLEAN");
        Button buttonSleep = new Button();
        // I'm not sure about that one
        if (character.isSleeping()){
            buttonSleep.setText("SLEEP");}
        else{
            buttonSleep.setText("WAKE UP");}


        buttonEat.setPrefSize(120, 60);
        buttonPlay.setPrefSize(120, 60);
        buttonClean.setPrefSize(120, 60);
        buttonSleep.setPrefSize(120, 60);

        String buttonStyle = "-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"; // it should go into fxml file
        buttonEat.setStyle(buttonStyle);
        buttonPlay.setStyle(buttonStyle);
        buttonClean.setStyle(buttonStyle);
        buttonSleep.setStyle(buttonStyle);

        // Button press event handlers
        buttonEat.setOnAction(e -> {
            character.eat();
            updateLabels();
            System.out.println("EAT");
        });
        buttonPlay.setOnAction(e -> {
            character.play();
            updateLabels();
            System.out.println("PLAY");
        });
        buttonClean.setOnAction(e -> {
            character.clean();
            updateLabels();
            System.out.println("CLEAN");
        });
        buttonSleep.setOnAction(e -> {
            if (character.isSleeping()){
                character.wakeUp();
                buttonSleep.setText("SLEEP");
                System.out.println("THEY WOKE UP");}
            else{
                buttonSleep.setText("WAKE UP");
                System.out.println("SLEEPY EPPY :3");}
            updateLabels();
        });

        HBox buttonContainer = new HBox(buttonEat, buttonPlay, buttonClean, buttonSleep);
        buttonContainer.setSpacing(30);
        buttonContainer.setAlignment(Pos.CENTER);

        double totalButtonWidth = (buttonEat.getPrefWidth() + buttonPlay.getPrefWidth() + buttonClean.getPrefWidth() + buttonSleep.getPrefWidth()) + (buttonContainer.getSpacing() * 3); // padding calc
        double padding = (800 - totalButtonWidth) / 2;
        buttonContainer.setPadding(new Insets(0, padding, 0, padding));
        buttonContainer.layoutYProperty().bind(bottomSection.heightProperty().subtract(buttonContainer.heightProperty()).divide(2));
        bottomSection.getChildren().add(buttonContainer);

        stage.setTitle("Javagotchi");
        stage.setScene(scene);
        stage.setResizable(false); // Block window resizing from users
        stage.show();


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            stage.setScene(scene);
            updateLabels(); // Refreshing scene every 10 seconds (mainly for top bars)
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateLabels() {
        nameLabel.setText("Energy: " + character.getEnergy());
        ageLabel.setText("Age: " + character.getAge());
        healthLabel.setText("Health: " + character.getHealth());
        hungerLabel.setText("Hunger: " + character.getHunger());
        cleanlinessLabel.setText("Cleanliness: " + character.getCleanliness());
        happinessLabel.setText("Happiness: " + character.getHappiness());
        energyLabel.setText("Energy: " + character.getEnergy());
    }

    public static void main(String[] args) {
        launch();
    }
}