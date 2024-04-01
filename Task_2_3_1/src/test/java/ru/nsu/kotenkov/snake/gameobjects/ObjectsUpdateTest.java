package ru.nsu.kotenkov.snake.gameobjects;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.snake.Controller;
import ru.nsu.kotenkov.snake.fxlogic.Playground;


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
        assertEquals(new Point(3, 2), testSnake.getHead());
    }
}
