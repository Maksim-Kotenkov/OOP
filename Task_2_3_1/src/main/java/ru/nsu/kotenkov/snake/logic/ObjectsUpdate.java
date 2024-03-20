package ru.nsu.kotenkov.snake.logic;


import ru.nsu.kotenkov.snake.gameObjects.Food;
import ru.nsu.kotenkov.snake.gameObjects.Snake;


public class ObjectsUpdate {
    private final Snake snake;
    private final Food food;

    public ObjectsUpdate(Snake snake, Food food) {
        this.snake = snake;
        this.food = food;
    }

    public void update() {
        snake.move();
        if (food.checkEaten(snake.getHead())) {
            snake.growNextTime();
        }
    }
}
