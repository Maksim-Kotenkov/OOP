package ru.nsu.kotenkov.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StageUpdate {
    public static void update(GraphicsContext context) {
        // reset
        context.setFill(Color.color(0, 0, 0));
        context.fillRect(0, 0, 100, 100);

        // for every snake cell print a rectangle
        // we need a method to print current cell
        // also, we should catch custom exception for intersections and show score + reset

        // as snake moved, we changed food locations, now print the food cells forEach

        // somehow print score from Snake.getLength
    }
}
