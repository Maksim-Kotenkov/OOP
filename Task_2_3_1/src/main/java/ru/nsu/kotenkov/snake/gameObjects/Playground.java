package ru.nsu.kotenkov.snake.gameObjects;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

//TODO maybe a record?
// and maybe load from config.json
public class Playground {
    // size
    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static int cellHeight = 10;
    public static int cellWidth = 10;
    public static int nCellsWidth = WIDTH / cellWidth;
    public static int nCellsHeight = HEIGHT / cellHeight;

    // colors
    public static Paint fontPaint = Color.BLACK;
    public static Paint foodPaint = Color.RED;
    public static Paint snakePaint = Color.GREEN;
    // TODO color for dead snake indicator

    // TODO number of food at the same time on the playground
}
