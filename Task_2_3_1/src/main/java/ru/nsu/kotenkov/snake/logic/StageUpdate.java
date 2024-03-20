package ru.nsu.kotenkov.snake.logic;


import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameObjects.Food;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;


public class StageUpdate implements Runnable {
    private final Snake snake;
    private final Food food;
    private final ObjectsUpdate objUpdater;
    private final GraphicsContext context;

    public StageUpdate(GraphicsContext context) {
        this.snake = new Snake();
        this.food = new Food();
        this.objUpdater = new ObjectsUpdate(snake, food);
        this.context = context;
    }

    @Override
    public void run() {
        try {
            while (true) {
                update();
                long toSleep = Math.max(Playground.basicFrameRate - (snake.getLength() * Playground.speedIncrease),
                        Playground.minFrameRate);
                Thread.sleep(toSleep);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }

    public void update() {
        // reset
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, Playground.WIDTH, Playground.HEIGHT);

        // update snake
        objUpdater.update();

        // for every snake cell print a rectangle
        // we need a method to print current cell
        // TODO also, we should catch custom exception for intersections and show score + reset
        context.setFill(Playground.snakePaint);
        snake.getCells().forEach(p -> printCell(context, p, Playground.snakePaint));

        context.setFill(Playground.foodPaint);
        food.getPoints().forEach(p -> printCell(context, p, Playground.foodPaint));

        // TODO somehow print score from Snake.getLength
    }

    private void printCell(GraphicsContext context, Point cell, Paint color) {
        context.setFill(color);
        if (color.equals(Playground.foodPaint)) {
            // TODO need drawImage(img, x, y, w, h)
            context.fillOval(cell.x * Playground.cellWidth,
                    cell.y * Playground.cellHeight,
                    Playground.cellWidth,
                    Playground.cellHeight);
        } else {
            context.fillRect(cell.x * Playground.cellWidth,
                    cell.y * Playground.cellHeight,
                    Playground.cellWidth,
                    Playground.cellHeight);
        }

    }

    public Snake getSnake() {
        return snake;
    }
}
