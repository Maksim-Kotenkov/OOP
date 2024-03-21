package ru.nsu.kotenkov.snake.logic;


import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameObjects.DeadSnakeException;
import ru.nsu.kotenkov.snake.gameObjects.Food;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;


public class StageUpdate implements Runnable {
    private Snake snake;
    private Food food;
    private ObjectsUpdate objUpdater;
    private final GraphicsContext context;
    private boolean resetButton;
    private boolean continueButton;

    public StageUpdate(GraphicsContext context) {
        this.snake = new Snake();
        this.food = new Food();
        this.objUpdater = new ObjectsUpdate(snake, food);
        this.context = context;
    }

    @Override
    public void run() {
        try {
            context.setFill(Playground.fontPaint);
            context.fillRect(0, 0, Playground.WIDTH, Playground.HEIGHT);
            startWaiting();

            while (true) {
                if (resetButton) {
                    resetButton = false;
                    reset();
                }

                try {
                    update();
                } catch (DeadSnakeException e) {
                    reset();
                    continue;
                }

                long toSleep = Math.max(Playground.basicFrameDelay - (snake.getLength() * Playground.speedIncrease),
                        Playground.minFrameDelay);
                Thread.sleep(toSleep);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }

    public void update() throws DeadSnakeException {
        // reset
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, Playground.WIDTH, Playground.HEIGHT);

        // update snake
        try {
            objUpdater.update();
        } catch (DeadSnakeException e) {
            gameOver();
            throw e;
        }

        // for every snake cell print a rectangle
        // we need a method to print current cell
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        printHead();

        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        context.setFill(Color.YELLOW);
        context.fillText("Score: " + snake.getLength(), Playground.WIDTH - 100, Playground.HEIGHT - 20);
    }

    private void printCell(Point cell, Paint color) {
        if (color.equals(Playground.foodPaint)) {
            context.setFill(color);
            // TODO need drawImage(img, x, y, w, h)
            context.fillOval(cell.x * Playground.cellWidth,
                    cell.y * Playground.cellHeight,
                    Playground.cellWidth,
                    Playground.cellHeight);
        } else if (color.equals(Playground.snakePaint)) {
            context.setStroke(color);
            context.strokeRect(cell.x * Playground.cellWidth,
                    cell.y * Playground.cellHeight,
                    Playground.cellWidth,
                    Playground.cellHeight);
        } else {
            context.setFill(color);
            context.fillRect(cell.x * Playground.cellWidth,
                    cell.y * Playground.cellHeight,
                    Playground.cellWidth,
                    Playground.cellHeight);
        }

    }

    private void printHead() {
        context.setFill(Playground.snakePaint);
        context.fillRect(snake.getHead().x * Playground.cellWidth,
                snake.getHead().y * Playground.cellHeight,
                Playground.cellWidth,
                Playground.cellHeight);
    }

    public Snake getSnake() {
        return snake;
    }

    private void gameOver() {
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        printCell(snake.getHead(), Playground.deadPaint);
    }

    private void reset() {
        // TODO change text size
        context.setFill(Playground.textPaint);
        context.fillText("Score: " + snake.getLength(),
                (double) Playground.WIDTH / 2 - 20,
                (double) Playground.HEIGHT / 2 - 20);

        startWaiting();

        this.snake = new Snake();
        this.food = new Food();
        this.objUpdater = new ObjectsUpdate(snake, food);
    }

    public void pressResetButton() {
        resetButton = true;
    }

    public void pressContinueButton() {
        synchronized (this){
            continueButton = true;
            notify();
        }
    }

    private void startWaiting() {
        // TODO change text size
        context.setFill(Playground.textPaint);
        context.fillText("PRESS ENTER TO START",
                (double) Playground.WIDTH / 2 - 70,
                (double) Playground.HEIGHT / 2);

        synchronized (this) {
            try {
                while (!continueButton) {
                    wait();
                }
                continueButton = false;
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
