package ru.nsu.kotenkov.snake.logic;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.snake.Controller;
import ru.nsu.kotenkov.snake.gameobjects.Food;
import ru.nsu.kotenkov.snake.gameobjects.ObjectsUpdate;
import ru.nsu.kotenkov.snake.gameobjects.Snake;


/**
 * Tests for game objects updater.
 */
public class ObjectsUpdateTest {
    @Test
    public void checkInit() {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);

        assertEquals(500, pg.width);
        assertEquals(500, pg.height);
    }

    @Test
    public void checkSnakeMovement() {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);
        Snake testSnake = new Snake(pg);
        Food food = new Food(testSnake.getCells(), pg);

        ObjectsUpdate updater = new ObjectsUpdate(testSnake, food, pg);

        assertDoesNotThrow(updater::update);
    }
}
