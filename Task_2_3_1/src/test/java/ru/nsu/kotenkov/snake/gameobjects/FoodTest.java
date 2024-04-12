package ru.nsu.kotenkov.snake.gameobjects;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Point;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.snake.Controller;
import ru.nsu.kotenkov.snake.fxlogic.Playground;


/**
 * Tests for Food class.
 */
public class FoodTest {

    @Test
    public void checkFoodNumber() {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);
        Snake testSnake = new Snake(pg);
        Food food = new Food(testSnake.getCells(), pg);

        assertEquals(pg.foodNumber, food.getPoints().size());
    }

    @Test
    public void checkRandomFoodCollisions() {
        Controller controller = new Controller();
        Playground pg = new Playground(controller);
        Snake testSnake = new Snake(pg);
        Food food = new Food(testSnake.getCells(), pg);

        Point randomNew = food.randomPoint(testSnake.getCells());

        assertFalse(food.getPoints().contains(randomNew));
        assertFalse(testSnake.getCells().contains(randomNew));
    }
}
