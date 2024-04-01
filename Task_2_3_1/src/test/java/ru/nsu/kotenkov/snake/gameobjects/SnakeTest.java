package ru.nsu.kotenkov.snake.gameobjects;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.snake.Controller;
import ru.nsu.kotenkov.snake.fxlogic.Playground;


/**
 * Tests for Snake class.
 */
public class SnakeTest {

    @Test
    public void checkMove() {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);
        Snake testSnake = new Snake(pg);

        assertDoesNotThrow(testSnake::move);
        assertEquals(new Point(3, 2), testSnake.getHead());
    }
}
