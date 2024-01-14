package com.javagotchi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*; // pos only prob?
import javafx.util.Duration;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;


public class Main extends Application {
    private Character character;
    private Label ageLabel;
    private Label healthLabel;
    private Label hungerLabel;
    private Label cleanlinessLabel;
    private Label happinessLabel;
    private Label energyLabel;
    private Label sleepLabel;
    private Label expLabel;
    private Label weigthLabel;
    private Label levelLabel;
    private Timeline timeline;
    private ImageView characterImageView;
    private int statsCounter = 0;
    private int hibernateCounter = 0;

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
        ageLabel = new Label("Age: " + character.getAge());
        healthLabel = new Label("Health: " + character.getHealth());
        hungerLabel = new Label("Hunger: " + character.getHunger());
        cleanlinessLabel = new Label("Cleanliness: " + character.getCleanliness());
        happinessLabel = new Label("Happiness: " + character.getHappiness());
        energyLabel = new Label("Energy: " + character.getEnergy());
        sleepLabel = new Label("Sleeping?: " + character.isSleeping());
        expLabel = new Label("Exp: " + character.getExperience());
        weigthLabel = new Label("Weigth: " + character.getWeight());
        levelLabel = new Label("Level: " + character.getLevel());

        String labelStyle = "-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';";
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
        sleepLabel.setLayoutX(280);
        sleepLabel.setLayoutY(20);
        sleepLabel.setStyle(labelStyle);
        expLabel.setLayoutX(280);
        expLabel.setLayoutY(50);
        expLabel.setStyle(labelStyle);
        weigthLabel.setLayoutX(280);
        weigthLabel.setLayoutY(80);
        weigthLabel.setStyle(labelStyle);
        levelLabel.setLayoutX(280);
        levelLabel.setLayoutY(110);
        levelLabel.setStyle(labelStyle);

        topSection.getChildren().addAll(ageLabel, healthLabel, hungerLabel, cleanlinessLabel, happinessLabel, energyLabel, sleepLabel, expLabel, weigthLabel, levelLabel);
        // Top section needs some cleaning up

        Pane bottomSection = new Pane();
        bottomSection.setPrefSize(800, 150);
        bottomSection.setStyle(containerBackground);

        Pane centerSection = new Pane();
        centerSection.setPrefSize(800, 300);

        // Load image as background
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream bg = classloader.getResourceAsStream("background3.jpg");
        if (bg == null) { 
            System.out.println("Error: image not found");
            System.exit(1);
        }
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(bg), 
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );

        //character.setLevel(3); //TEST

        centerSection.setBackground(new Background(backgroundImage));
        String characterImageString = "small.png";
        if (character.getLevel() > 2 && character.getLevel() < 5){
            characterImageString = "mid.png";}
        else if (character.getLevel() >= 5){
            characterImageString = "big.png";}
        InputStream characterImage = classloader.getResourceAsStream(characterImageString);
        if (characterImage == null) { 
            System.out.println("Error: character image not found");
            System.exit(1);
        }
        characterImageView = new ImageView(new Image(characterImage));
        characterImageView.setFitWidth(200);
        characterImageView.setPreserveRatio(true);
        characterImageView.setLayoutX((centerSection.getPrefWidth() - characterImageView.getFitWidth()) / 2); // Center the small image horizontally
        characterImageView.setLayoutY((centerSection.getPrefHeight() - characterImageView.getFitHeight()) / 2); // Center the small image vertically

        centerSection.getChildren().add(characterImageView);

        

        VBox root = new VBox(topSection, centerSection, bottomSection);
        scene.setRoot(root);

        Button buttonEat = new Button("EAT");
        Button buttonPlay = new Button("PLAY");
        Button buttonClean = new Button("CLEAN");
        Button buttonSleep = new Button("SLEEP");
        // I'm not sure about that one


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
            updateLabels();
            System.out.println("PLAY");
            showBrickBreakerGame(stage);
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
                System.out.println("THEY WOKE UP");
                buttonEat.setDisable(character.isSleeping());
                buttonPlay.setDisable(character.isSleeping());
                buttonClean.setDisable(character.isSleeping());
            }
            else{
                character.sleep();
                buttonSleep.setText("WAKE UP");
                System.out.println("SLEEPY EPPY :3");
                buttonEat.setDisable(character.isSleeping());
                buttonPlay.setDisable(character.isSleeping());
                buttonClean.setDisable(character.isSleeping());
            }
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
        stage.setResizable(false); // Block window resizing from user
        stage.show();

        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                stage.setScene(scene);
                statsCounter++;
                if (statsCounter >= 2) {
                    updateStats();
                    statsCounter = 0;
                }
                updateLabels(); // Refreshing labels every 1 seconds
                healthCheck();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        
        private void healthCheck() {
            if (character.getHealth() <= 1) {
                System.out.println("YOUR CHARACTER IS DEAD");
                InputStream deadImage = getClass().getClassLoader().getResourceAsStream("dead.png");
                if (deadImage == null) { 
                    System.out.println("Error: dead image not found");
                    System.exit(1);
                }
                characterImageView.setImage(new Image(deadImage));
                
                timeline.stop();
        
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText(null);
                    alert.setContentText("YOUR CHARACTER IS DEAD");
                    alert.showAndWait();
                    System.exit(0);
                });
            }
        }
  
        private void updateStats() {
            if (character.isSleeping()) {
                hibernateCounter++;
                if (hibernateCounter >= 5) {
                    character.setHunger(character.getHunger() - 1);
                    character.setCleanliness(character.getCleanliness() - 1);
                    character.setHappiness(character.getHappiness() - 1);
                    character.setExperience(character.getExperience() + 1);
                    hibernateCounter = 0;
                }
            }
            else {
                character.setEnergy(character.getEnergy() - 1);
                character.setHunger(character.getHunger() - 1);
                character.setCleanliness(character.getCleanliness() - 1);
                character.setHappiness(character.getHappiness() - 1);
                character.setExperience(character.getExperience() + 1);
            }
            
            character.setWeight(character.getWeight() - 1);
            if(character.getExperience() >= 5){
                character.setLevel(character.getLevel() + 1);
                character.setExperience(0);
            }

            // Image update
            if(character.getLevel() > 2 && character.getLevel() < 5){
                InputStream midImage = getClass().getClassLoader().getResourceAsStream("mid.png");
                if (midImage == null) { 
                    System.out.println("Error: mid image not found");
                    System.exit(1);
                }
                characterImageView.setImage(new Image(midImage));
            }
            else if(character.getLevel() >= 5){
                InputStream bigImage = getClass().getClassLoader().getResourceAsStream("big.png");
                if (bigImage == null) { 
                    System.out.println("Error: big image not found");
                    System.exit(1);
                }
                characterImageView.setImage(new Image(bigImage));
            }
        }

        private void updateLabels() {
            ageLabel.setText("Age: " + character.getAge());
            healthLabel.setText("Health: " + character.getHealth());
            hungerLabel.setText("Hunger: " + character.getHunger());
            cleanlinessLabel.setText("Cleanliness: " + character.getCleanliness());
            happinessLabel.setText("Happiness: " + character.getHappiness());
            energyLabel.setText("Energy: " + character.getEnergy());
            sleepLabel.setText("Sleeping?: " + character.isSleeping());
            expLabel.setText("Exp: " + character.getExperience());
            weigthLabel.setText("Weigth: " + character.getWeight());
            levelLabel.setText("Level: " + character.getLevel());
        
    }
    /**
     * Displays the Brick Breaker game window.
     *
     * @param primaryStage the primary stage of the application
     */
    private void showBrickBreakerGame(Stage primaryStage) {
        Stage brickBreakerStage = new Stage();
        brickBreakerStage.initModality(Modality.WINDOW_MODAL);
        brickBreakerStage.initOwner(primaryStage);

        BrickBreakerGame brickBreakerGame = new BrickBreakerGame();
        brickBreakerGame.start(brickBreakerStage);
        brickBreakerStage.setOnCloseRequest(event -> {

            System.out.println("Brick Breaker Game window closed.");
        });

        brickBreakerStage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
