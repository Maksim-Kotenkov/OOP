package ru.nsu.kotenkov.snake;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;
import ru.nsu.kotenkov.snake.logic.StageUpdate;

import java.io.IOException;
import java.util.Objects;


// TODO scale playground with window size
// TODO check for updating the screen with controller or smth
// TODO or create JavaFX threads (not java threads)
public class SnakeApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(Objects.requireNonNull(Objects.requireNonNull(getClass().getResource("/Snake.fxml")).openStream()));
        Controller controller = fxmlLoader.getController();

        Canvas canvas = controller.getPlaygroundCanvas();

        // init sizes of everything
        Playground playground = new Playground(controller);

        // init context
        GraphicsContext context = canvas.getGraphicsContext2D();

        StageUpdate updater = new StageUpdate(context, playground);
        controller.setUpdater(updater);

        Scene scene = getScene(root, updater, controller);

        primaryStage.setResizable(true);
        primaryStage.setTitle("Snake");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread gameLoop = new Thread(updater);
        gameLoop.start();
    }

    private static Scene getScene(Pane root, StageUpdate updater, Controller controller) {
        Scene scene = new Scene(root);

        root.setOnKeyPressed(action -> {
            Snake snake = updater.getSnake();
            switch (action.getCode()) {
                case W:
                    snake.setUp();
                    break;
                case S:
                    snake.setDown();
                    break;
                case A:
                    snake.setLeft();
                    break;
                case D:
                    snake.setRight();
                    break;
                case R:
                    updater.pressResetButton();
                    break;
            }
        });
        return scene;
    }
}
