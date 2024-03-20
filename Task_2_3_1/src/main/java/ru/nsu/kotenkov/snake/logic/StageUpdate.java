package ru.nsu.kotenkov.snake.logic;


import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameObjects.Food;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;


public class StageUpdate {
    private final Snake snake;
    private final Food food;
    private final ObjectsUpdate objUpdater;

    public StageUpdate() {
        this.snake = new Snake();
        this.food = new Food();
        this.objUpdater = new ObjectsUpdate(snake, food);
    }

    public void update(GraphicsContext context) {
        // reset
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, Playground.WIDTH, Playground.HEIGHT);

        // update snake
        objUpdater.update();

        // for every snake cell print a rectangle
        // we need a method to print current cell
        // TODO also, we should catch custom exception for intersections and show score + reset
        snake.getCells().forEach(p -> printCell(context, p, Playground.snakePaint));

        food.getPoints().forEach(p -> printCell(context, p, Playground.foodPaint));

        // TODO somehow print score from Snake.getLength
    }

    private void printCell(GraphicsContext context, Point cell, Paint color) {
        context.setFill(color);
        context.fillRect(cell.x * Playground.cellWidth,
                cell.y * Playground.cellHeight,
                Playground.cellWidth,
                Playground.cellHeight);
    }
}
