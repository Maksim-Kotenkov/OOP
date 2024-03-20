package ru.nsu.kotenkov.snake;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameObjects.Food;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;

import java.awt.*;

public class StageUpdate {
    private final Snake snake;
    private final Food food;

    public StageUpdate(Snake snake) {
        this.snake = snake;
        this.food = new Food();
    }

    public void update(GraphicsContext context) {
        // reset
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, Playground.WIDTH, Playground.HEIGHT);

        // for every snake cell print a rectangle
        // we need a method to print current cell
        // also, we should catch custom exception for intersections and show score + reset
        snake.getCells().forEach(p -> printCell(context, p, Playground.snakePaint));

        // as snake moved, we changed food locations, now print the food cells forEach
        food.getPoints().forEach(p -> printCell(context, p, Playground.foodPaint));

        // somehow print score from Snake.getLength
    }

    private void printCell(GraphicsContext context, Point cell, Paint color) {
        context.setFill(color);
        context.fillRect(cell.x * Playground.cellWidth,
                cell.y * Playground.cellHeight,
                Playground.cellWidth,
                Playground.cellHeight);
    }
}
