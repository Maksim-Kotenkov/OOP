package ru.nsu.kotenkov.snake.logic;


import ru.nsu.kotenkov.snake.gameobjects.exceptions.DeadSnakeException;
import ru.nsu.kotenkov.snake.gameobjects.Food;
import ru.nsu.kotenkov.snake.gameobjects.Playground;
import ru.nsu.kotenkov.snake.gameobjects.Snake;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.VictorySignal;


/**
 * Updater for game objects (snake, food).
 */
public class ObjectsUpdate {
    private final Snake snake;
    private final Food food;
    private final Playground playground;

    /**
     * Constructor.
     *
     * @param snake snake obj
     * @param food food obj
     * @param playground playground parameters
     */
    public ObjectsUpdate(Snake snake, Food food, Playground playground) {
        this.snake = snake;
        this.food = food;
        this.playground = playground;
    }

    /**
     * Move snake and check conditions.
     * If any problem happens - throw exception.
     *
     * @throws DeadSnakeException snake is dead
     * @throws VictorySignal snake is fed enough
     */
    public void update() throws DeadSnakeException, VictorySignal {
        snake.move();
        if (food.checkEaten(snake.getCells())) {
            snake.growNextTime();
        }

        if (snake.getLength() >= playground.victoryScore) {
            throw new VictorySignal();
        }
    }
}
