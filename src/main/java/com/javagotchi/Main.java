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
    //private Label sleepLabel;
    //private Label expLabel;
    private Label weigthLabel;
    private Label levelLabel;

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
        VBox section1 = new VBox();
        section1.setPrefSize(200, 150);
        section1.setLayoutX(0);
        section1.setLayoutY(0);
        section1.setStyle("-fx-background-color: #663399;");

        VBox section2 = new VBox();
        section2.setPrefSize(200, 150);
        section2.setLayoutX(200);
        section2.setLayoutY(0);
        section2.setStyle("-fx-background-color: #9932CC;");

        VBox section3 = new VBox();
        section3.setPrefSize(200, 150);
        section3.setLayoutX(400);
        section3.setLayoutY(0);
        section3.setStyle("-fx-background-color: #8A2BE2;");

        VBox section4 = new VBox();
        section4.setPrefSize(200, 150);
        section4.setLayoutX(600);
        section4.setLayoutY(0);
        section4.setStyle("-fx-background-color: #9370DB;");

        // Labels for section 1
        Label ageLabel = new Label("Age: " + character.getAge());
        Label healthLabel = new Label("Health: " + character.getHealth());
        ageLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        healthLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        section1.getChildren().addAll(ageLabel, healthLabel);

        // Labels for section 2
        Label hungerLabel = new Label("Hunger: " + character.getHunger());
        Label cleanlinessLabel = new Label("Cleanliness: " + character.getCleanliness());
        hungerLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        cleanlinessLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        section2.getChildren().addAll(hungerLabel, cleanlinessLabel);

        // Labels for section 3
        Label happinessLabel = new Label("Happiness: " + character.getHappiness());
        Label energyLabel = new Label("Energy: " + character.getEnergy());
        happinessLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        energyLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        section3.getChildren().addAll(happinessLabel, energyLabel);

        // Labels for section 4
        Label weigthLabel = new Label("Weigth: " + "Underweight");
        Label levelLabel = new Label("Level: " + character.getLevel());
        weigthLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        levelLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");
        section4.getChildren().addAll(weigthLabel, levelLabel);

        topSection.getChildren().addAll(section1, section2, section3, section4);


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

        //character.setLevel(3); // TEST

        centerSection.setBackground(new Background(backgroundImage));
        String characterImageString = "small.png";
        if (character.getLevel() > 2 && character.getLevel() < 5){
            characterImageString = "mid.png";}
        else if (character.getLevel() > 5){
            characterImageString = "big.png";}
        InputStream characterImage = classloader.getResourceAsStream(characterImageString);
        if (characterImage == null) { 
            System.out.println("Error: character image not found");
            System.exit(1);
        }
        ImageView characterImageView = new ImageView(new Image(characterImage));
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
        stage.setResizable(false); // Block window resizing from users
        stage.show();




        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            stage.setScene(scene);
            updateLabels(); // Refreshing scene every 10 seconds (mainly for top bars)
            healthCheck(); // Checking if character is dead
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void healthCheck() {
        if (character.getHealth() <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("YOUR CHARACTER IS DEAD");
            alert.showAndWait();
            System.exit(0);
        }
    }

    private void updateLabels() {
        String weigthText = "None";
        if (character.getWeight() <= 30){
            weigthText = "Underweight";
        }
        else if (character.getWeight() > 30 && character.getWeight() <= 150){
            weigthText = "Regular";
        }
        else if (character.getWeight() > 150 && character.getWeight() <= 300){
            weigthText = "Overweight";
        }
        else if (character.getWeight() > 300){
            weigthText = "Obese";
        }

        //ageLabel.setText("Age: " + character.getAge()); // what is wrong with this
        healthLabel.setText("Health: " + character.getHealth());
        hungerLabel.setText("Hunger: " + character.getHunger());
        cleanlinessLabel.setText("Cleanliness: " + character.getCleanliness());
        happinessLabel.setText("Happiness: " + character.getHappiness());
        energyLabel.setText("Energy: " + character.getEnergy());
        weigthLabel.setText("Weigth: " + weigthText);
        levelLabel.setText("Level: " + character.getLevel());
    }

    

    public static void main(String[] args) {
        launch();
    }
}
