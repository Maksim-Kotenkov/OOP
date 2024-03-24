package ru.nsu.kotenkov.snake.gameObjects;


import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.Controller;

/**
 * A class that turns fxml config into parameters for snake game.
 */
public class Playground {
    // size
    public int WIDTH;
    public int HEIGHT;
    public int cellHeight = 50;
    public int cellWidth = 50;
    public int nCellsWidth;
    public int nCellsHeight;

    // game setup
    public int foodNumber = 3;
    public int victoryScore = 15;
    private Controller controller;

    /**
     * At the initializing of the game we calculate everything.
     *
     * @param controller controller with initialized parts
     */
    public Playground(Controller controller) {
        this.controller = controller;

        // parameters of a canvas
        Canvas canvas = controller.getPlaygroundCanvas();

        WIDTH = (int) canvas.getWidth();
        HEIGHT = (int) canvas.getHeight();
        nCellsWidth = WIDTH / cellWidth;
        nCellsHeight = HEIGHT / cellHeight;


    }

    public void setCustomizableFields() {
        // parameters from fields
        TextField foodNumberField = controller.getFoodNumberField();
        TextField VictoryScoreField = controller.getVictoryScoreField();

        if (!foodNumberField.getCharacters().isEmpty()) {
            foodNumber = Integer.parseInt(foodNumberField.getCharacters().toString());
        }
        if (!VictoryScoreField.getCharacters().isEmpty()) {
            victoryScore = Integer.parseInt(VictoryScoreField.getCharacters().toString());
        }
    }

    // colors
    public static Paint fontPaint = Color.BLACK;
    public static Paint foodPaint = Color.RED;
    public static Paint snakePaint = Color.GREEN;
    public static Paint deadPaint = Color.ORANGERED;
    public static Paint textPaint = Color.YELLOW;

    public static long basicFrameDelay = 300;
    public static long speedIncrease = 20;
    public static long minFrameDelay = 100;
}
