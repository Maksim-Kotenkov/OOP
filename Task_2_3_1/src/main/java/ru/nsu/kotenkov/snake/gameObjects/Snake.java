package ru.nsu.kotenkov.snake.gameObjects;


import java.awt.Point;
import java.util.ArrayList;


public class Snake {
    private ArrayList<Point> cells;
    // there is a class Point so better use it

    // length

    // method to add new point in the beginning of the list and deleting last one
    // while adding new point, check eating food with method of Food class (give our cords of the new head)
    // also checking for intersections and throwing custom error

    // setters for the movement direction

    public Snake() {
        cells = new ArrayList<>();
        cells.add(new Point(2, 2));
        cells.add(new Point(2, 3));
    }

    public ArrayList<Point> getCells() {
        return cells;
    }
}
