package ru.nsu.kotenkov.snake;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;
import ru.nsu.kotenkov.snake.logic.StageUpdate;


public class SnakeApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(Playground.WIDTH, Playground.HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);
        StageUpdate updater = new StageUpdate(context);

        canvas.setOnKeyPressed(action -> {
            Snake snake = updater.getSnake();
            switch (action.getCode()) {
                case UP:
                    snake.setUp();
                    break;
                case DOWN:
                    snake.setDown();
                    break;
                case LEFT:
                    snake.setLeft();
                    break;
                case RIGHT:
                    snake.setRight();
                    break;
                case ENTER:
                    updater.pressContinueButton();
                    break;
                case R:
                    updater.pressResetButton();
                    break;
            }
        });

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Snake");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread gameLoop = new Thread(updater);
        gameLoop.start();
    }
}
