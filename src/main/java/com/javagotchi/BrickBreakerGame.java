package com.javagotchi;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BrickBreakerGame extends Application {
    private static final int WIDTH = 380;
    private static final int HEIGHT = 600;
    private static final int PADDLE_WIDTH = 60;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_RADIUS = 5;
    public static final int NUM_BRICKS_HORIZONTAL = 6;
    public static final int NUM_BRICKS_VERTICAL = 5;
    private static final int BRICK_WIDTH = 55;
    private static final int BRICK_HEIGHT = 20;
    private Pane root;
    private Rectangle paddle;
    private Circle ball;
    private Line roof;
    private Rectangle roofBackground;
    private Character character = new Character();
    private Label levelLabel;
    private Label ageLabel;
    private Label healthLabel;
    private Label hungerLabel;
    private Label cleanlinessLabel;
    private Label happinessLabel;
    private Label energyLabel;
    private Label experienceLabel;
    private double ballSpeedX = 0;
    private double ballSpeedY = 0;
    private boolean gameOver = false;
    private boolean gameStart = true;
    private int score = 0;
    private Label scoreLabel;
    Timeline timeline;

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Initializes and starts the Brick Breaker Game.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Brick Breaker Game");
        root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setResizable(false);

        createPaddle();
        createBall();
        createBricks();
        createRoof();
        createScoreLabel();
        createStatsLabels();
        scene.setOnKeyPressed(e -> {
            if (!gameOver) {
                if (e.getCode() == KeyCode.LEFT) {
                    movePaddleLeft();
                } else if (e.getCode() == KeyCode.RIGHT) {
                    movePaddleRight();
                }
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (!gameOver) {
                moveBall();
                checkCollision();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        primaryStage.setScene(scene);
        primaryStage.show();

        checkGameStart(primaryStage);
        checkGameOver(primaryStage);
        checkGameComplete(primaryStage);
    }
    /**
     * Creates a paddle object and adds it to the root pane.
     * The paddle is positioned at the bottom center of the screen.
     */
    private void createPaddle() {
        paddle = new Rectangle(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_HEIGHT - 40, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFill(Color.BLUE);
        root.getChildren().add(paddle);
    }
    /**
     * Creates a new ball and adds it to the root pane.
     */
    private void createBall() {
        ball = new Circle(WIDTH / 2, HEIGHT / 2, BALL_RADIUS, Color.RED);
        root.getChildren().add(ball);
    }
    /**
     * Creates and adds bricks to the root pane.
     * <p>
     * The bricks are created in a grid pattern with a specified horizontal and vertical gap between them.
     * The position of each brick is calculated based on its index in the grid.
     * The created bricks are filled with the color purple.
     */
    private void createBricks() {
        int horizontalGap = 10;
        int verticalGap = 5;
        int brickOffsetY = 82;

        for (int i = 0; i < NUM_BRICKS_HORIZONTAL; i++) {
            for (int j = 0; j < NUM_BRICKS_VERTICAL; j++) {
                double x = i * (BRICK_WIDTH + horizontalGap);
                double y = j * (BRICK_HEIGHT + verticalGap) + brickOffsetY;

                Rectangle brick = new Rectangle(x, y, BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFill(Color.PURPLE);
                root.getChildren().add(brick);
            }
        }
    }
    /**
     * Creates a score label and adds it to the root pane.
     */
    private void createScoreLabel() {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(new Font(16));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(WIDTH / 2 - 45);
        scoreLabel.setLayoutY(10);
        root.getChildren().add(scoreLabel);
    }
    /**
     * Creates and initializes the stats labels for the character.
     */
    private void createStatsLabels() {
        levelLabel = new Label("Level: " + character.getLevel());
        experienceLabel = new Label("Experience: " + character.getExperience());
        ageLabel = new Label("Age: " + character.getAge());
        healthLabel = new Label("Health: " + character.getHealth());
        hungerLabel = new Label("Hunger: " + character.getHunger());
        cleanlinessLabel = new Label("Cleanliness: " + character.getCleanliness());
        happinessLabel = new Label("Happiness: " + character.getHappiness());
        energyLabel = new Label("Energy: " + character.getEnergy());

        levelLabel.setLayoutX(10);
        levelLabel.setLayoutY(10);
        levelLabel.setTextFill(Color.WHITE);
        experienceLabel.setLayoutX(10);
        experienceLabel.setLayoutY(25);
        experienceLabel.setTextFill(Color.WHITE);
        healthLabel.setLayoutX(10);
        healthLabel.setLayoutY(40);
        healthLabel.setTextFill(Color.WHITE);
        hungerLabel.setLayoutX(10);
        hungerLabel.setLayoutY(55);
        hungerLabel.setTextFill(Color.WHITE);
        cleanlinessLabel.setLayoutX(300);
        cleanlinessLabel.setLayoutY(10);
        cleanlinessLabel.setTextFill(Color.WHITE);
        happinessLabel.setLayoutX(300);
        happinessLabel.setLayoutY(25);
        happinessLabel.setTextFill(Color.WHITE);
        energyLabel.setLayoutX(300);
        energyLabel.setLayoutY(40);
        energyLabel.setTextFill(Color.WHITE);
        ageLabel.setLayoutX(300);
        ageLabel.setLayoutY(55);
        ageLabel.setTextFill(Color.WHITE);
        root.getChildren().addAll(levelLabel, ageLabel, experienceLabel, healthLabel, hungerLabel, cleanlinessLabel, happinessLabel, energyLabel);
    }
    /**
     * Creates a line that separates a stats from a game
     */
    private void createRoof() {
        roofBackground = new Rectangle(0, 0, WIDTH, 80);
        roofBackground.setFill(Color.GRAY);
        root.getChildren().add(roofBackground);
        roof = new Line(0, 80, WIDTH, 80);
        roof.setFill(Color.BLACK);
        root.getChildren().add(roof);

    }
    /**
     * Moves the paddle to the left by 20 units, if it is not already at the leftmost position.
     */
    private void movePaddleLeft() {
        if (paddle.getX() > 0) {
            paddle.setX(paddle.getX() - 20);
        }
    }
    /**
     * Moves the paddle to the right if it is not already at the rightmost position.
     */
    private void movePaddleRight() {
        if (paddle.getX() < WIDTH - PADDLE_WIDTH) {
            paddle.setX(paddle.getX() + 20);
        }
    }
    /**
     * Moves the ball by updating its position based on the current speed.
     * If the ball reaches the left or right boundary of the screen, it changes direction horizontally.
     * If the ball reaches the top boundary of the screen, it changes direction vertically.
     * If the ball reaches the bottom boundary of the screen, the game is over.
     */
    private void moveBall() {
        ball.setCenterX(ball.getCenterX() + ballSpeedX);
        ball.setCenterY(ball.getCenterY() + ballSpeedY);

        if (ball.getCenterX() < 0 || ball.getCenterX() > WIDTH) {
            ballSpeedX *= -1;
        }

        if (ball.getCenterY() - ball.getRadius() <= 80) {
            ballSpeedY *= -1;
        }
        if (ball.getCenterY() >= HEIGHT) {
            ballSpeedY = 0;
            ballSpeedX = 0;
            System.out.println("Game Over");
            gameOver = true;

        }
    }
    /**
     * Checks for collision between the ball and the paddle, as well as the ball and the bricks.
     * If a collision is detected with the paddle, the ball's vertical speed is reversed.
     * If a collision is detected with a brick, the brick is removed from the scene, the ball's vertical speed is reversed,
     * the score is incremented, and the score is updated.
     */
    private void checkCollision() {
        if (ball.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
            ballSpeedY *= -1;
        }
        for (javafx.scene.Node node : root.getChildren()) {
            if (node instanceof Rectangle && node != paddle) {
                Rectangle brick = (Rectangle) node;
                if (ball.getBoundsInParent().intersects(brick.getBoundsInParent())) {
                    root.getChildren().remove(brick);
                    ballSpeedY *= -1;
                    score++;
                    updateScore();
                    break;
                }
            }
        }
    }
    /**
     * Updates the score label with the current score.
     */
    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }
    /**
     * Updates the labels in the user interface with the current values of the character's attributes.
     * The labels include experience, age, health, hunger, cleanliness, happiness, energy, and level.
     */
    private void updateLabels() {
        experienceLabel.setText("Experience: " + character.getExperience());
        ageLabel.setText("Age: " + character.getAge());
        healthLabel.setText("Health: " + character.getHealth());
        hungerLabel.setText("Hunger: " + character.getHunger());
        cleanlinessLabel.setText("Cleanliness: " + character.getCleanliness());
        happinessLabel.setText("Happiness: " + character.getHappiness());
        energyLabel.setText("Energy: " + character.getEnergy());
        levelLabel.setText("Level: " + character.getLevel());
    }
    /**
     * Checks if the game is over and performs necessary actions.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    private void checkGameOver(Stage primaryStage) {
        Timeline gameOverTimeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (gameOver) {
                //TODO:PLAY METHOD FROM CHARACTER!
                updateLabels();
                displayGameOver(primaryStage);
            }
        }));
        gameOverTimeline.setCycleCount(Timeline.INDEFINITE);
        gameOverTimeline.play();
    }
    /**
     * Checks if the game has started and updates the labels accordingly.
     * If the game has started, it displays the game start screen on the primary stage.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    private void checkGameStart(Stage primaryStage) {
        Timeline gameStartTimeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (gameStart) {
                updateLabels();
                displayGameStart(primaryStage);
            }
        }));
        gameStartTimeline.setCycleCount(Timeline.INDEFINITE);
        gameStartTimeline.play();
    }
    /**
     * Checks if the game is complete and performs necessary actions if the condition is met.
     *
     * @param primaryStage the primary stage of the game
     */
    private void checkGameComplete(Stage primaryStage) {
        Timeline gameCompleteTimeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (score == 30) {
                //TODO:PLAY METHOD FROM CHARACTER!
                ballSpeedY = 0;
                ballSpeedX = 0;
                updateLabels();
                displayGameComplete(primaryStage);
            }
        }));
        gameCompleteTimeline.setCycleCount(Timeline.INDEFINITE);
        gameCompleteTimeline.play();
    }
    /**
     * Displays the game over screen with the given stage.
     *
     * @param primaryStage the primary stage of the application
     */
    private void displayGameOver(Stage primaryStage) {
        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(new Font(60));
        gameOverText.setFill(Color.RED);
        gameOverText.setX((WIDTH - gameOverText.getBoundsInLocal().getWidth()) / 2);
        gameOverText.setY(HEIGHT / 2 - 50);

        Text pressEnterToQuit = new Text("Press Escape to Quit");
        pressEnterToQuit.setFont(new Font(25));
        pressEnterToQuit.setFill(Color.BLUE);
        pressEnterToQuit.setX(WIDTH / 2 - 110);
        pressEnterToQuit.setY(HEIGHT / 2 + 170);

        Text pressSpaceToTryAgain = new Text("Press Space to try again");
        pressSpaceToTryAgain.setFont(new Font(25));
        pressSpaceToTryAgain.setFill(Color.BLUE);
        pressSpaceToTryAgain.setX(WIDTH / 2 - 120);
        pressSpaceToTryAgain.setY(HEIGHT / 2 + 200);

        Text yourScoreWas = new Text("Your Score: " + score);
        yourScoreWas.setFont(new Font(25));
        yourScoreWas.setFill(Color.BLUE);
        yourScoreWas.setX(WIDTH / 2 - 70);
        yourScoreWas.setY(HEIGHT / 2 + 60);

        root.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            } else if (keyEvent.getCode() == KeyCode.SPACE) {
                restartGame();
                timeline.stop();
                primaryStage.close();
                start(new Stage());

            }
        });
        root.getChildren().addAll(gameOverText, pressEnterToQuit, pressSpaceToTryAgain, yourScoreWas);
    }
    /**
     * Displays the game start screen with the specified Stage.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    private void displayGameStart(Stage primaryStage) {
        Text gameStartText = new Text("Start a game!");
        gameStartText.setFont(new Font(60));
        gameStartText.setFill(Color.RED);
        gameStartText.setX((WIDTH - gameStartText.getBoundsInLocal().getWidth()) / 2);
        gameStartText.setY(HEIGHT / 2 - 50);

        Text pressEnterToQuit = new Text("Press Escape to Quit");
        pressEnterToQuit.setFont(new Font(25));
        pressEnterToQuit.setFill(Color.BLUE);
        pressEnterToQuit.setX(WIDTH / 2 - 110);
        pressEnterToQuit.setY(HEIGHT / 2 + 170);

        Text pressSpaceToTryAgain = new Text("Press Space to start a game");
        pressSpaceToTryAgain.setFont(new Font(25));
        pressSpaceToTryAgain.setFill(Color.BLUE);
        pressSpaceToTryAgain.setX(WIDTH / 2 - 140);
        pressSpaceToTryAgain.setY(HEIGHT / 2 + 200);

        root.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            } else if (keyEvent.getCode() == KeyCode.SPACE) {
                restartGame();
                timeline.stop();
                primaryStage.close();
                gameStart = false;
                start(new Stage());

            }
        });

        root.getChildren().addAll(gameStartText, pressEnterToQuit, pressSpaceToTryAgain);
    }
    /**
     * Displays the game completion screen with appropriate text and instructions.
     *
     * @param primaryStage the primary stage of the JavaFX application
     */
    private void displayGameComplete(Stage primaryStage) {
        Text gameCompleteText = new Text("Game\nCompleted!");
        gameCompleteText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        gameCompleteText.setFont(new Font(60));
        gameCompleteText.setFill(Color.GREEN);
        gameCompleteText.setX((WIDTH - gameCompleteText.getBoundsInLocal().getWidth()) / 2);
        gameCompleteText.setY(HEIGHT / 2 - 50);

        Text pressEnterToQuit = new Text("Press Escape to Quit");
        pressEnterToQuit.setFont(new Font(25));
        pressEnterToQuit.setFill(Color.BLUE);
        pressEnterToQuit.setX(WIDTH / 2 - 110);
        pressEnterToQuit.setY(HEIGHT / 2 + 170);

        Text pressSpaceToTryAgain = new Text("Press Space to start a new game");
        pressSpaceToTryAgain.setFont(new Font(25));
        pressSpaceToTryAgain.setFill(Color.BLUE);
        pressSpaceToTryAgain.setX(WIDTH / 2 - 170);
        pressSpaceToTryAgain.setY(HEIGHT / 2 + 200);

        root.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            } else if (keyEvent.getCode() == KeyCode.SPACE) {
                restartGame();
                timeline.stop();
                primaryStage.close();
                gameStart = false;
                start(new Stage());
            }
        });

        root.getChildren().addAll(gameCompleteText, pressEnterToQuit, pressSpaceToTryAgain);
    }
    /**
     * Restarts the game by resetting the game state and creating new paddle, ball, and bricks.
     */
    private void restartGame() {
        gameOver = false;
        ballSpeedX = -1.5;
        ballSpeedY = -1.5;
        score = 0;
        root.getChildren().clear();
        createPaddle();
        createBall();
        createBricks();
    }
}