package com.javagotchi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.*; // pos only prob?
import javafx.util.Duration;
import lombok.ToString;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Optional;
import java.net.URL;
import java.awt.Desktop;
import java.net.URI;


public class Main extends Application {
    private Character character;
    private DataBase dataBase;
    private Label ageLabel;
    private Label healthLabel;
    private Label hungerLabel;
    private Label cleanlinessLabel;
    private Label happinessLabel;
    private Label energyLabel;
    private Label weigthLabel;
    private Label levelLabel;
    private Label hibernationLabel;
    private Timeline timeline;
    private ImageView characterImageView;
    private int statsCounter = 0;
    private int hibernateCounter = 0;
    private int weightCounter = 0;

    @Override
    public void start(Stage stage) throws IOException {
        character = Character.getInstance();
        dataBase = DataBase.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        String containerBackground = "-fx-background-color: #44394d;";// it should go to fxml file
        Pane topSection = new Pane();
        topSection.setPrefSize(800, 150);
        topSection.setStyle(containerBackground);

        // Test labels
        ageLabel = new Label("AGE: " + character.getAge());
        healthLabel = new Label("HEALTH: " + character.getHealth());
        hungerLabel = new Label("HUNGER: " + character.getHunger());
        cleanlinessLabel = new Label("CLEANLINESS: " + cleanlinessCheck(character.getCleanliness()));
        happinessLabel = new Label("HAPPINESS: " + happinessCheck(character.getHappiness()));
        energyLabel = new Label("ENERGY: " + character.getEnergy());
        weigthLabel = new Label("WEIGHT: " + weightCheck(character.getWeight()));
        levelLabel = new Label(String.format("LEVEL: %d (%s)", character.getLevel(), levelCheck(character.getLevel())));

        String labelStyle = "-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';";
        healthLabel.setLayoutX(660);
        healthLabel.setLayoutY(10);
        healthLabel.setStyle(labelStyle);
        healthLabel.setFont(Font.font("Helvetica", 18));

        hungerLabel.setLayoutX(660);
        hungerLabel.setLayoutY(40);
        hungerLabel.setStyle(labelStyle);
        hungerLabel.setFont(Font.font("Helvetica", 18));

        energyLabel.setLayoutX(660);
        energyLabel.setLayoutY(70);
        energyLabel.setStyle(labelStyle);
        energyLabel.setFont(Font.font("Helvetica", 18));

        levelLabel.setLayoutX(30);
        levelLabel.setLayoutY(100);
        levelLabel.setStyle(labelStyle);
        levelLabel.setFont(Font.font("Helvetica", 18));


        cleanlinessLabel.setLayoutX(30);
        cleanlinessLabel.setLayoutY(10);
        cleanlinessLabel.setStyle(labelStyle);
        cleanlinessLabel.setFont(Font.font("Helvetica", 18));

        happinessLabel.setLayoutX(30);
        happinessLabel.setLayoutY(40);
        happinessLabel.setStyle(labelStyle);
        happinessLabel.setFont(Font.font("Helvetica", 18));

        weigthLabel.setLayoutX(30);
        weigthLabel.setLayoutY(70);
        weigthLabel.setStyle(labelStyle);
        weigthLabel.setFont(Font.font("Helvetica", 18));

        ageLabel.setLayoutX(660);
        ageLabel.setLayoutY(100);
        ageLabel.setStyle(labelStyle);
        ageLabel.setFont(Font.font("Helvetica", 18));

        
        hibernationLabel = new Label("");
        hibernationLabel.setLayoutX(310);
        hibernationLabel.setLayoutY(100);
        hibernationLabel.setStyle("-fx-text-fill: #84b390;");
        hibernationLabel.setFont(Font.font("Helvetica", 24));


        String github = "/github.png";
        Image githubImage = new Image(getClass().getResource(github).toExternalForm());
        ImageView githubImageView = new ImageView(githubImage);
        githubImageView.setFitHeight(30);
        githubImageView.setFitWidth(30);
        Hyperlink link = new Hyperlink();
        link.setGraphic(githubImageView);
        link.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
        link.setOnAction(event -> {
            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://github.com/JakJaj/Javagotchi"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        link.setLayoutX(380);
        link.setLayoutY(5);

        topSection.getChildren().addAll(link, ageLabel, healthLabel, hungerLabel, cleanlinessLabel, happinessLabel, energyLabel, weigthLabel, levelLabel, hibernationLabel);

        Pane bottomSection = new Pane();
        bottomSection.setPrefSize(800, 150);
        bottomSection.setStyle(containerBackground);

        Pane centerSection = new Pane();
        centerSection.setPrefSize(800, 300);

        // Load image as background
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream bg = classloader.getResourceAsStream("background.jpg");
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
        if (character.getLevel() > 2 && character.getLevel() <= 5){
            characterImageString = "mid.png";}
        else if (character.getLevel() > 5){
            characterImageString = "big.png";}
        InputStream characterImage = classloader.getResourceAsStream(characterImageString);
        if (characterImage == null) { 
            System.out.println("Error: character image not found");
            System.exit(1);
        }
        characterImageView = new ImageView(new Image(characterImage));
        characterImageView.setFitWidth(200);
        characterImageView.setPreserveRatio(true);
        characterImageView.setLayoutX((centerSection.getPrefWidth() - characterImageView.getFitWidth()) / 2);
        characterImageView.setLayoutY((centerSection.getPrefHeight() - characterImageView.getFitHeight()) / 2);
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

        String buttonStyle = "-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';";
        buttonEat.setOnMouseEntered(e -> buttonEat.setStyle("-fx-background-color: #504557; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonPlay.setOnMouseEntered(e -> buttonPlay.setStyle("-fx-background-color: #504557; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonClean.setOnMouseEntered(e -> buttonClean.setStyle("-fx-background-color: #504557; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonSleep.setOnMouseEntered(e -> buttonSleep.setStyle("-fx-background-color: #504557; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonEat.setOnMouseExited(e -> buttonEat.setStyle("-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonPlay.setOnMouseExited(e -> buttonPlay.setStyle("-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonClean.setOnMouseExited(e -> buttonClean.setStyle("-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        buttonSleep.setOnMouseExited(e -> buttonSleep.setStyle("-fx-background-color: #2a232e; -fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';"));
        
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
            ColorAdjust nightTime = new ColorAdjust();
            if (character.isSleeping()){
                character.wakeUp();
                buttonSleep.setText("SLEEP");
                System.out.println("THEY WOKE UP");
                buttonEat.setDisable(character.isSleeping());
                buttonPlay.setDisable(character.isSleeping());
                buttonClean.setDisable(character.isSleeping());
                nightTime.setBrightness(0.0);
                centerSection.setEffect(nightTime);
                hibernationLabel.setText("");
            }
            else{
                character.sleep();
                buttonSleep.setText("WAKE UP");
                System.out.println("SLEEPY EPPY :3");
                buttonEat.setDisable(character.isSleeping());
                buttonPlay.setDisable(character.isSleeping());
                buttonClean.setDisable(character.isSleeping());
                nightTime.setBrightness(-0.6);
                centerSection.setEffect(nightTime);
                hibernationLabel.setText("HIBERNATING . . .");
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

        String bgmusicFile = "/bgmusic.mp3";

        Media sound = new Media(getClass().getResource(bgmusicFile).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
        
        
        stage.setTitle("Javagotchi");
        stage.setScene(scene);
        stage.setResizable(false); // Block window resizing from user
        stage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.P) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play();
                }
            }
            else if (event.getCode() == KeyCode.ESCAPE) {
                // Database save here
                System.exit(0);
            }
        });
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                stage.setScene(scene);
                statsCounter++;
                if (statsCounter >= 1) {
                    updateStats();
                    statsCounter = 0;
                }
                updateLabels(); // Refreshing labels every 1 seconds
                healthCheck();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

        stage.setOnCloseRequest(event -> {
            if (dataBase.open()){
                dataBase.insertNewestData(character);
                System.out.println("Update database using a current stats");
            }
            System.out.println("Javagotchi closed!");
        });
        }
        
        private String causeOfDeath(){
            if (character.getHunger() <= 0){
                return "STARVATION";
            }
            else if (character.getHappiness() <= 0){
                return "MENTAL HEALTH ISSUES";
            }
            else if (character.getEnergy() <= 0){
                return "EXAUSTION";
            }
            else{
                return "UNKNOWN CAUSE";
            }
        }

        private void healthCheck() {
            if (character.getHunger() <= 0 || character.getHappiness() <= 0 || character.getEnergy() <= 0) {
                character.setHealth(Math.max(character.getHealth() - 1, 0));
            }
            else if (character.getHunger() >= 50 && character.getHappiness() >= 50 && character.getEnergy() >= 50) {
                character.setHealth(Math.min(character.getHealth() + 1, 100));
            }

            if (character.getHealth() <= 0) {
                System.out.println("YOUR CHARACTER IS DEAD");
                InputStream deadImage = getClass().getClassLoader().getResourceAsStream("dead.png");
                if (deadImage == null) { 
                    System.out.println("Error: dead image not found");
                    System.exit(1);
                }
                characterImageView.setImage(new Image(deadImage));
                int score = character.getAge() * 100 + character.getLevel() * 100 + character.getExperience();
                
                timeline.pause();
                
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText(null);
                    alert.setContentText(String.format("YOUR CHARACTER HAS DIED OF %s :(( \nSCORE: %d \nWould you like to reset the game or exit?", causeOfDeath(), score));

                    ButtonType buttonTypeReset = new ButtonType("Reset");
                    ButtonType buttonTypeExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeReset, buttonTypeExit);

                    Optional<ButtonType> result = alert.showAndWait();
                    resetGame();
                    if (result.get() == buttonTypeReset){   
                        timeline.play();
                    } else {
                        System.exit(0);
                    }
                });
            }
        }


        private void updateStats() {
            if (character.isSleeping()) {
                hibernateCounter++;
                if (hibernateCounter >= 5) {
                    character.setHunger(Math.max(character.getHunger() - 1, 0));
                    character.setCleanliness(Math.max(character.getCleanliness() - 1, 0));
                    character.setHappiness(Math.max(character.getHappiness() - 1, 0));
                    character.setExperience(character.getExperience() + 1);
                    hibernateCounter = 0;
                }
            }
            else {
                character.setEnergy(Math.max(character.getEnergy() - 1, 0));
                character.setHunger(Math.max(character.getHunger() - 1, 0));
                character.setCleanliness(Math.max(character.getCleanliness() - 1, 0));
                character.setHappiness(Math.max(character.getHappiness() - 1, 0));
                character.setExperience(character.getExperience() + 1);
            }
            if (character.getHunger() <= 20){
                character.setWeight(character.getWeight() - 1);
            }else{
                weightCounter++;
                if (weightCounter >= 5){
                    character.setWeight(character.getWeight() - 1);
                    weightCounter = 0;
                }
            }
            if(character.getExperience() >= 5){
                character.setLevel(character.getLevel() + 1);
                character.setExperience(0);
            }

            // Image update
            if(character.getLevel() > 2 && character.getLevel() <= 5){
                InputStream midImage = getClass().getClassLoader().getResourceAsStream("mid.png");
                if (midImage == null) { 
                    System.out.println("Error: mid image not found");
                    System.exit(1);
                }
                characterImageView.setImage(new Image(midImage));
            }
            else if(character.getLevel() > 5){
                InputStream bigImage = getClass().getClassLoader().getResourceAsStream("big.png");
                if (bigImage == null) { 
                    System.out.println("Error: big image not found");
                    System.exit(1);
                }
                characterImageView.setImage(new Image(bigImage));
            }
        }

        private void resetGame() {
            if(dataBase.open()) {
                dataBase.resetDatabase();
            }
            character = DataBase.getInstance().getLatestCharacterData();
            characterImageView.setImage(new Image(getClass().getClassLoader().getResourceAsStream("small.png")));
            updateLabels();
        }

        private String fontColor(int value) {
            if (value <= 20)
                return "-fx-text-fill: #ff6969; -fx-font-family: 'Helvetica';";
            else if (value <= 40)
                return "-fx-text-fill: #f2eba7; -fx-font-family: 'Helvetica';";
            else 
                return "-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';";
        }

        private String weightCheck(int value){
            if (value <= 10)
                return "STARVED";
            else if (value <= 30)
                return "UNDERWEIGHT";
            else if (value <= 300)
                return "NORMAL";
            else if (value <= 500)
                return "OVERWEIGHT";
            else
                return "OBESE";
        }

        private String levelCheck(int value){
            if (value <= 2)
                return "BABY";
            else if (value <= 5)
                return "CHILD";
            else if (value <= 10)
                return "TEEN";
            else
                return "FULLY GROWN";
        }

        private String cleanlinessCheck(int value){
            if (value <= 5)
                return "DIRTY";
            else if (value <= 20)
                return "SMELLY";
            else if (value <= 70)
                return "NORMAL";
            else if (value <= 90)
                return "CLEAN";
            else
                return "SPARKLING";
        }

        private String happinessCheck(int value){
            if (value <= 5)
                return "DEPRESSED";
            else if (value <= 20)
                return "SAD";
            else if (value <= 70)
                return "NORMAL";
            else if (value <= 90)
                return "HAPPY";
            else
                return "ECSTATIC";
        }

        private void updateLabels() {
            ageLabel.setText("AGE: " + character.getAge());
            healthLabel.setText("HEALTH: " + character.getHealth());
            hungerLabel.setText("HUNGER: " + character.getHunger());
            cleanlinessLabel.setText("CLEANLINESS: " + cleanlinessCheck(character.getCleanliness()));
            happinessLabel.setText("HAPPINESS: " + happinessCheck(character.getHappiness()));
            energyLabel.setText("ENERGY: " + character.getEnergy());
            weigthLabel.setText("WEIGHT: " + weightCheck(character.getWeight()));
            levelLabel.setText(String.format("LEVEL: %d (%s)", character.getLevel(), levelCheck(character.getLevel())));
            healthLabel.setStyle(fontColor(character.getHealth()));
            hungerLabel.setStyle(fontColor(character.getHunger()));
            cleanlinessLabel.setStyle(fontColor(character.getCleanliness()));
            happinessLabel.setStyle(fontColor(character.getHappiness()));
            energyLabel.setStyle(fontColor(character.getEnergy()));
            if (character.getWeight() >= 500 || character.getWeight() <= 20) {
                weigthLabel.setStyle("-fx-text-fill: #ff6969; -fx-font-family: 'Helvetica';");
            } else {
                weigthLabel.setStyle("-fx-text-fill: #f2f2f2; -fx-font-family: 'Helvetica';");}
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
