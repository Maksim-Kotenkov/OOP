package ru.nsu.kotenkov.snake.gameObjects;


import java.awt.Point;
import java.util.ArrayList;


public class Snake {
    private enum directionEnum {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }
    private final ArrayList<Point> cells;
    private int length;
    private directionEnum direction = directionEnum.RIGHT;
    private Point movingVector = new Point(1, 0);
    private boolean growNextTime;

    public Snake() {
        cells = new ArrayList<>();
        cells.add(new Point(2, 2));
        cells.add(new Point(2, 3));
    }

    public void move() {
        Point newPoint = new Point(cells.get(0).x + movingVector.x, cells.get(0).y + movingVector.y);

        if (newPoint.x < 0) {
            newPoint.x = Playground.nCellsWidth;
        } else if (newPoint.x > Playground.nCellsWidth) {
            newPoint.x = 0;
        }

        if (newPoint.y < 0) {
            newPoint.y = Playground.nCellsHeight;
        } else if (newPoint.y > Playground.nCellsHeight) {
            newPoint.y = 0;
        }

        cells.add(0, newPoint);

        if (!growNextTime) {
            cells.remove(cells.size() - 1);
        } else {
            length += 1;
            growNextTime = false;
        }
    }

    // TODO also checking for intersections and throwing custom error

    public Point getHead() {
        return cells.get(0);
    }

    public ArrayList<Point> getCells() {
        return cells;
    }

    public void setUp() {
        if (!direction.equals(directionEnum.DOWN)) {
            direction = directionEnum.UP;
            movingVector = new Point(0, -1);
        }
    }

    public void setRight() {
        if (!direction.equals(directionEnum.LEFT)) {
            direction = directionEnum.RIGHT;
            movingVector = new Point(1, 0);
        }
    }

    public void setDown() {
        if (!direction.equals(directionEnum.UP)) {
            direction = directionEnum.DOWN;
            movingVector = new Point(0, 1);
        }
    }

    public void setLeft() {
        if (!direction.equals(directionEnum.RIGHT)) {
            direction = directionEnum.LEFT;
            movingVector = new Point(-1, 0);
        }
    }

    public void growNextTime() {
        growNextTime = true;
    }

    public int getLength() {
        return length;
    }
}
