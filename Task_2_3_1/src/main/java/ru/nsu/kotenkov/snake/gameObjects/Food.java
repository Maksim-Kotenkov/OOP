package ru.nsu.kotenkov.snake.gameObjects;

import java.awt.*;
import java.util.ArrayList;

public class Food {
    // list of points for many foods
    // point
    private ArrayList<Point> points;

    public Food() {
        points = new ArrayList<>();
        points.add(new Point(5, 5));
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    // change pos to random

    // check eating the food (get only coordinates of the new head of the snake and return boolean)
}
