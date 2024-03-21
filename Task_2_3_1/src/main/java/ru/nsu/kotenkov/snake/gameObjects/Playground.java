package ru.nsu.kotenkov.snake.gameObjects;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

//TODO maybe a record?
// and maybe load from config.json
public class Playground {
    // size
    public static int WIDTH = 800;
    public static int HEIGHT = 800;
    public static int cellHeight = 50;
    public static int cellWidth = 50;
    public static int nCellsWidth = WIDTH / cellWidth;
    public static int nCellsHeight = HEIGHT / cellHeight;

    // colors
    public static Paint fontPaint = Color.BLACK;
    public static Paint foodPaint = Color.RED;
    public static Paint snakePaint = Color.GREEN;
    public static Paint deadPaint = Color.ORANGERED;
    public static Paint textPaint = Color.YELLOW;

    // TODO number of food at the same time on the playground

    public static long basicFrameDelay = 300;
    public static long speedIncrease = 10;
    public static long minFrameDelay = 100;
    public static long resetSleep = 1000;
}
