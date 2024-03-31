package ru.nsu.kotenkov.snake.logic;


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
    public int Width;
    public int Height;
    public int cellHeight = 50;
    public int cellWidth = 50;
    public int numberCellsWidth = 10;
    public int numberCellsHeight = 10;
    private int maxNumCellsWidth = 100;
    private int maxNumCellsHeight = 100;

    // game setup
    public int foodNumber = 3;
    public int victoryScore = 15;
    public static long basicFrameDelay = 300;
    public static long speedIncrease = 20;
    public static long minFrameDelay = 100;
    private final Controller controller;

    // colors
    public static Paint fontPaint = Color.BLACK;
    public static Paint foodPaint = Color.RED;
    public static Paint snakePaint = Color.GREEN;
    public static Paint deadPaint = Color.ORANGERED;
    public static Paint textPaint = Color.YELLOW;


    /**
     * At the initializing of the game we calculate everything.
     *
     * @param controller controller with initialized parts
     */
    public Playground(Controller controller) {
        this.controller = controller;

        // parameters of a canvas
        Canvas canvas = controller.getPlaygroundCanvas();

        if (canvas == null) {
            Width = 500;
            Height = 500;
        } else {
            Width = (int) canvas.getWidth();
            Height = (int) canvas.getHeight();
        }
    }

    /**
     * Print score to the score field.
     *
     * @param newScore new val
     */
    public void setScore(int newScore) {
        controller.getScoreField().setText(String.valueOf(newScore));
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
            numberCellsWidth = Math.min(maxNumCellsWidth,
                    Integer.parseInt(widthCellsField.getCharacters().toString())
            );
            cellWidth = Width / numberCellsWidth;
        }
        if (!heightCellsField.getCharacters().isEmpty()) {
            numberCellsHeight = Math.min(maxNumCellsHeight,
                    Integer.parseInt(heightCellsField.getCharacters().toString())
            );
            cellHeight = Height / numberCellsHeight;
        }
    }
}
