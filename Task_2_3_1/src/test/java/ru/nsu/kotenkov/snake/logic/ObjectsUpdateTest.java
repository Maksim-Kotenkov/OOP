package ru.nsu.kotenkov.snake.logic;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.snake.Controller;
import ru.nsu.kotenkov.snake.gameobjects.Food;
import ru.nsu.kotenkov.snake.gameobjects.Snake;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.DeadSnakeException;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.VictorySignal;


public class ObjectsUpdateTest {
    @Test
    public void checkInit() {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);

        assertEquals(500, pg.Width);
        assertEquals(500, pg.Height);
    }

    @Test
    public void checkSnakeMovement() throws DeadSnakeException, VictorySignal {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);
        Snake testSnake = new Snake(pg);
        Food food = new Food(testSnake.getCells(), pg);

        ObjectsUpdate updater = new ObjectsUpdate(testSnake, food, pg);

        assertDoesNotThrow(updater::update);
    }
}
