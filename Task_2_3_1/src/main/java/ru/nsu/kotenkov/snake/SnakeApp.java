package ru.nsu.kotenkov.snake;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class SnakeApp extends Application {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext context = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);

        StageUpdate.update(context);

        pane.getChildren().add(canvas);

        Scene scene = new Scene(pane);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Snake");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private void reset() {
//        grid = new Grid(WIDTH, HEIGHT);
//        loop = new GameLoop(grid, context);
//        Painter.paint(grid, context);
//    }
}
