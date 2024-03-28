package ru.nsu.kotenkov.snake.logic;


import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameobjects.Food;
import ru.nsu.kotenkov.snake.gameobjects.Playground;
import ru.nsu.kotenkov.snake.gameobjects.Snake;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.DeadSnakeException;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.VictorySignal;


/**
 * A class to be run in separated thread. It changes the context of the playground.
 */
public class StageUpdate implements Runnable {
    private Snake snake;
    private Food food;
    private ObjectsUpdate objUpdater;
    private final GraphicsContext context;
    private final Playground playground;
    private boolean resetButton;  // changes its condition on R key and "Reset" button
    private boolean continueButton;  // changes its condition on "Start" button

    /**
     * Constructor.
     *
     * @param context the context from canvas
     * @param playground playground parameters
     */
    public StageUpdate(GraphicsContext context, Playground playground) {
        this.playground = playground;
        this.snake = new Snake(playground);
        this.food = new Food(snake.getCells(), playground);
        this.objUpdater = new ObjectsUpdate(snake, food, playground);
        this.context = context;
    }

    /**
     * Life cycle.
     */
    @Override
    public void run() {
        try {
            // setting up playground and printing start message, then waiting for start events
            context.setFill(Playground.fontPaint);
            context.fillRect(0, 0, playground.Width, playground.Height);
            printStartMessage();
            startWaiting();

            // when user starts the game we get their setup from fields
            playground.setCustomizableFields();
            this.food.reInitFood(this.snake.getCells());

            // infinite loop with resets on losing game and pressing reset
            while (true) {
                if (resetButton) {
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

                // don't sleep more than min threshold
                long toSleep = Math.max(Playground.basicFrameDelay
                                - (snake.getLength() * Playground.speedIncrease),
                        Playground.minFrameDelay);
                Thread.sleep(toSleep);
            }
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }

    }

    /**
     * Method to update context.
     * Move snake, spawn food, check conditions.
     *
     * @throws DeadSnakeException exception to signal about death
     * @throws VictorySignal exception to signal about victory
     */
    public void update() throws DeadSnakeException, VictorySignal {
        // reset everything
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, playground.Width, playground.Height);

        // update snake
        try {
            objUpdater.update();
        } catch (DeadSnakeException e) {
            gameOver();
            throw e;
        }

        // for every snake cell print a rectangle, for food - circle
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        printHead();  // head is different

        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        // TODO move score to the field
        context.setFill(Color.YELLOW);
        context.fillText("Score: " + snake.getLength(),
                playground.Width - 100,
                playground.Height - 20);
    }

    /**
     * Print a cell on the canvas with the color given.
     * We can understand what shape will we spawn depending on the color.
     *
     * @param cell Point object
     * @param color Paint object
     */
    private void printCell(Point cell, Paint color) {
        // deciding what shape to print depending on the color
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

    /**
     * Printing head is a little different from printing anything, but color is the same.
     * This is why there is separated from printCell method.
     */
    private void printHead() {
        context.setFill(Playground.snakePaint);
        context.fillRect(snake.getHead().x * playground.cellWidth,
                snake.getHead().y * playground.cellHeight,
                playground.cellWidth,
                playground.cellHeight);
    }

    /**
     * Print playground with dead snake.
     */
    private void gameOver() {
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        printCell(snake.getHead(), Playground.deadPaint);
    }

    /**
     * Reset the game.
     *
     * @param victory was it the victory or just reset
     */
    private void reset(boolean victory) {
        // TODO change text size
        printScore(victory);
        printStartMessage();
        startWaiting();

        playground.setCustomizableFields();
        this.snake = new Snake(playground);
        this.food = new Food(snake.getCells(), playground);
        this.objUpdater = new ObjectsUpdate(snake, food, playground);
        resetButton = false;
    }

    /**
     * Event handlers for buttons/keys.
     */
    public void pressResetButton() {
        resetButton = true;
    }

    /**
     * Event handlers for buttons.
     */
    public void pressContinueButton() {
        synchronized (this) {
            continueButton = true;
            notify();
        }
    }

    /**
     * At some moments we wait for the user to start the game, so, there is a method.
     */
    private void startWaiting() {
        // TODO change text size
        continueButton = false;
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

    /**
     * Printing messages on the canvas.
     * The message is different for reset and victory.
     *
     * @param victory flag for satisfied victory conditions
     */
    private void printScore(boolean victory) {
        context.setFill(Playground.textPaint);
        if (victory) {
            context.fillText("CONGRATULATIONS, YOU'VE REACHED SCORE "
                            + playground.victoryScore + " AND WON!!!",
                    (double) playground.Width / 2 - 200,
                    (double) playground.Height / 2 - 20);
        } else {
            context.fillText("Score: " + snake.getLength(),
                    (double) playground.Width / 2 - 20,
                    (double) playground.Height / 2 - 20);
        }
    }

    /**
     * Printing welcome/reset message on the canvas.
     */
    private void printStartMessage() {
        context.setFill(Playground.textPaint);
        context.fillText("PRESS \"START\" BUTTON TO START A NEW GAME",
                (double) playground.Width / 2 - 165,
                (double) playground.Height / 2);
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public Snake getSnake() {
        return snake;
    }
}
