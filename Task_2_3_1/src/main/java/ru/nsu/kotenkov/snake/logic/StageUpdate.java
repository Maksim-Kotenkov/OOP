package ru.nsu.kotenkov.snake.logic;


import java.awt.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import ru.nsu.kotenkov.snake.gameobjects.Food;
import ru.nsu.kotenkov.snake.gameobjects.ObjectsUpdate;
import ru.nsu.kotenkov.snake.gameobjects.Snake;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.DeadSnakeException;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.ToWaitException;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.VictorySignal;


/**
 * A class to be run in separated thread. It changes the context of the playground.
 */
public class StageUpdate {
    private Snake snake;
    private Food food;
    private ObjectsUpdate objUpdater;
    private final GraphicsContext context;
    private final Playground playground;

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
    public void run() throws ToWaitException {
        // font
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, playground.width, playground.height);

        // updating canvas
        try {
            update();
        } catch (DeadSnakeException e) {
            reset(false, true);
        } catch (VictorySignal e) {
            reset(true, false);
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
        context.fillRect(0, 0, playground.width, playground.height);

        // update snake
        objUpdater.update();

        // for every snake cell print a rectangle, for food - circle
        snake.getCells().forEach(p -> printCell(p, Playground.snakePaint));
        printHead();  // head is different

        food.getPoints().forEach(p -> printCell(p, Playground.foodPaint));

        playground.setScore(snake.getLength());
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
     * Throw exception to force SceneTimer to wait for the start.
     *
     * @param victory was it the victory or just reset
     */
    public void reset(boolean victory, boolean dead) throws ToWaitException {
        context.setFill(Playground.fontPaint);
        context.fillRect(0, 0, playground.width, playground.height);

        if (victory) {
            printScore();
        } else if (dead) {
            gameOver();
        }
        printStartMessage();

        playground.setCustomizableFields();
        this.snake = new Snake(playground);
        this.food = new Food(snake.getCells(), playground);
        this.objUpdater = new ObjectsUpdate(snake, food, playground);

        throw new ToWaitException();
    }


    /**
     * Printing victory messages on the canvas.
     */
    private void printScore() {
        playground.setScore(snake.getLength());
        context.setFill(Playground.textPaint);
        context.fillText("CONGRATULATIONS, YOU'VE REACHED SCORE "
                        + playground.victoryScore + " AND WON!!!",
                (double) playground.width / 2 - 200,
                (double) playground.height / 2 - 20);
    }

    /**
     * Printing welcome/reset message on the canvas.
     */
    private void printStartMessage() {
        context.setFill(Playground.textPaint);
        context.fillText("PRESS \"START\" BUTTON TO START A NEW GAME",
                (double) playground.width / 2 - 165,
                (double) playground.height / 2);
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
