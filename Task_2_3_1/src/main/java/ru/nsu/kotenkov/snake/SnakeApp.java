package ru.nsu.kotenkov.snake;


import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.nsu.kotenkov.snake.gameobjects.Snake;
import ru.nsu.kotenkov.snake.fxlogic.Playground;
import ru.nsu.kotenkov.snake.fxlogic.SceneTimer;
import ru.nsu.kotenkov.snake.fxlogic.StageUpdate;


/**
 * Application class.
 * The whole screen is resizable, but the game field is not.
 * (if the size of the window is smaller than the playground, a part of it is beyond the edges)
 */
public class SnakeApp extends Application {

    /**
     * No comments.
     *
     * @param args no comments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Mandatory Application start method.
     *
     * @param primaryStage the whole window stage
     * @throws IOException while reading configuration
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Pane root = fxmlLoader.load(Objects.requireNonNull(Objects.requireNonNull(
                getClass().getResource("/Snake.fxml")).openStream())
        );
        Controller controller = fxmlLoader.getController();

        Canvas canvas = controller.getPlaygroundCanvas();

        // init sizes of everything
        Playground playground = new Playground(controller);

        // init context
        GraphicsContext context = canvas.getGraphicsContext2D();

        StageUpdate updater = new StageUpdate(context, playground);
        SceneTimer sceneTimer = new SceneTimer(updater);

        // set up objects we've just created
        controller.setTimer(sceneTimer);

        Scene scene = getScene(root, updater, sceneTimer);

        primaryStage.setResizable(true);
        primaryStage.setTitle("Snake");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();

        // now the game loop is managed by AnimationTimer
        sceneTimer.start();
    }

    /**
     * Scene setup method.
     *
     * @param root root pane
     * @param updater updater class to bind methods
     * @return initialized scene
     */
    private static Scene getScene(Pane root, StageUpdate updater, SceneTimer timer) {
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
                    timer.pressResetButton();
                    break;
                default:
                    break;
            }
        });
        return scene;
    }
}
