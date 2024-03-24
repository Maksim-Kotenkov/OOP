package ru.nsu.kotenkov.snake.logic;


import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameObjects.exceptions.DeadSnakeException;
import ru.nsu.kotenkov.snake.gameObjects.Food;
import ru.nsu.kotenkov.snake.gameObjects.Playground;
import ru.nsu.kotenkov.snake.gameObjects.Snake;
import ru.nsu.kotenkov.snake.gameObjects.exceptions.VictorySignal;


public class StageUpdate implements Runnable {
    private Snake snake;
    private Food food;
    private ObjectsUpdate objUpdater;
    private final GraphicsContext context;
    private final Playground playground;
    private boolean resetButton;
    private boolean continueButton;

    public StageUpdate(GraphicsContext context, Playground playground) {
        this.playground = playground;
        this.snake = new Snake(playground);
        this.food = new Food(snake.getCells(), playground);
        this.objUpdater = new ObjectsUpdate(snake, food, playground);
        this.context = context;
    }

    @Override
    public void run() {
        try {
            context.setFill(Playground.fontPaint);
            context.fillRect(0, 0, playground.WIDTH, playground.HEIGHT);
            startWaiting();

            // when user starts the game we get their setup
            playground.setCustomizableFields();
            this.food.reInitFood(this.snake.getCells());

            while (true) {
                if (resetButton) {
                    resetButton = false;
                    reset(false);
                }

                try {
                    update();
                } catch (DeadSnakeException e) {
                    reset(false);
                    continue;
                } catch (VictorySignal e) {
                    reset(true);
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

    public void update() throws DeadSnakeException, VictorySignal {
        // reset
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, playground.WIDTH, playground.HEIGHT);

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
        context.fillText("Score: " + snake.getLength(), playground.WIDTH - 100, playground.HEIGHT - 20);
    }

    private void printCell(Point cell, Paint color) {
        if (color.equals(Playground.foodPaint)) {
            context.setFill(color);
            // TODO need drawImage(img, x, y, w, h)
            context.fillOval(cell.x * playground.cellWidth,
                    cell.y * playground.cellHeight,
                    playground.cellWidth,
                    playground.cellHeight);
        } else if (color.equals(Playground.snakePaint)) {
            context.setStroke(color);
            context.strokeRect(cell.x * playground.cellWidth,
                    cell.y * playground.cellHeight,
                    playground.cellWidth,
                    playground.cellHeight);
        } else {
            context.setFill(color);
            context.fillRect(cell.x * playground.cellWidth,
                    cell.y * playground.cellHeight,
                    playground.cellWidth,
                    playground.cellHeight);
        }

    }

    private void printHead() {
        context.setFill(Playground.snakePaint);
        context.fillRect(snake.getHead().x * playground.cellWidth,
                snake.getHead().y * playground.cellHeight,
                playground.cellWidth,
                playground.cellHeight);
    }

    public Snake getSnake() {
        return snake;
    }

    private void gameOver() {
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        printCell(snake.getHead(), Playground.deadPaint);
    }

    private void victory() {
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        context.setFill(Playground.textPaint);
        context.fillText("CONGRATULATIONS, YOU'VE REACHED " + playground.victoryScore + " AND WON!!!",
                (double) playground.WIDTH / 2 - 20,
                (double) playground.HEIGHT / 2 - 20);
    }

    private void reset(boolean victory) {
        // TODO change text size
        context.setFill(Playground.textPaint);
        if (victory) {
            context.fillText("CONGRATULATIONS, YOU'VE REACHED SCORE " + playground.victoryScore + " AND WON!!!",
                    (double) playground.WIDTH / 2 - 200,
                    (double) playground.HEIGHT / 2 - 20);
        } else {
            context.fillText("Score: " + snake.getLength(),
                    (double) playground.WIDTH / 2 - 20,
                    (double) playground.HEIGHT / 2 - 20);
        }

        startWaiting();
        playground.setCustomizableFields();

        this.snake = new Snake(playground);
        this.food = new Food(snake.getCells(), playground);
        this.objUpdater = new ObjectsUpdate(snake, food, playground);
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
                (double) playground.WIDTH / 2 - 70,
                (double) playground.HEIGHT / 2);

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
