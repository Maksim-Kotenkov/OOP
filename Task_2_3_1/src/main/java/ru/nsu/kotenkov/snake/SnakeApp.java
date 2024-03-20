package ru.nsu.kotenkov.snake;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
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

        StageUpdate updater = new StageUpdate();

        updater.update(context);
        // TODO how to set this method on cycle?

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Snake");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
