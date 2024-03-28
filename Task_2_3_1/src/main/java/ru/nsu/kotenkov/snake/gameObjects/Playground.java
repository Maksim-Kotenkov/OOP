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
    public int nCellsWidth = 10;
    public int nCellsHeight = 10;
    private int maxNCellsWidth = 100;
    private int maxNCellsHeight = 100;

    // game setup
    public int foodNumber = 3;
    public int victoryScore = 15;
    private final Controller controller;

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
    }

    /**
     * After reset and start we need to set parameters to filed values.
     */
    public void setCustomizableFields() {
        // parameters from fields
        TextField foodNumberField = controller.getFoodNumberField();
        TextField VictoryScoreField = controller.getVictoryScoreField();
        TextField widthCellsField = controller.getWidthCellsField();
        TextField heightCellsField = controller.getHeightCellsField();

        if (!foodNumberField.getCharacters().isEmpty()) {
            foodNumber = Integer.parseInt(foodNumberField.getCharacters().toString());
        }
        if (!VictoryScoreField.getCharacters().isEmpty()) {
            victoryScore = Integer.parseInt(VictoryScoreField.getCharacters().toString());
        }

        if (!widthCellsField.getCharacters().isEmpty()) {
            nCellsWidth = Math.min(maxNCellsWidth,
                    Integer.parseInt(widthCellsField.getCharacters().toString())
            );
            cellWidth = WIDTH / nCellsWidth;
        }
        if (!heightCellsField.getCharacters().isEmpty()) {
            nCellsHeight = Math.min(maxNCellsHeight,
                    Integer.parseInt(heightCellsField.getCharacters().toString())
            );
            cellHeight = HEIGHT / nCellsHeight;
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
