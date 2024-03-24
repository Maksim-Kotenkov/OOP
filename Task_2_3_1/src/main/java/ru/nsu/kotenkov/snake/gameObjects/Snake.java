package ru.nsu.kotenkov.snake.gameObjects;


import ru.nsu.kotenkov.snake.gameObjects.exceptions.DeadSnakeException;

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
    private final Playground playground;
    private int length = 2;
    private directionEnum direction = directionEnum.RIGHT;
    private directionEnum prevDirection = directionEnum.RIGHT;
    private Point movingVector = new Point(1, 0);
    private boolean growNextTime;

    public Snake(Playground playground) {
        cells = new ArrayList<>();
        cells.add(new Point(2, 2));
        cells.add(new Point(2, 3));
        this.playground = playground;
    }

    public void move() throws DeadSnakeException {
        Point newPoint = new Point(cells.get(0).x + movingVector.x, cells.get(0).y + movingVector.y);

        if (newPoint.x < 0) {
            newPoint.x = playground.nCellsWidth - 1;
        } else if (newPoint.x > playground.nCellsWidth - 1) {
            newPoint.x = 0;
        }

        if (newPoint.y < 0) {
            newPoint.y = playground.nCellsHeight - 1;
        } else if (newPoint.y > playground.nCellsHeight - 1) {
            newPoint.y = 0;
        }

        if (!growNextTime) {
            cells.remove(cells.size() - 1);
        } else {
            length += 1;
            growNextTime = false;
        }

        if (cells.stream().anyMatch(newPoint::equals)) {
            cells.add(0, newPoint);  // to color the head with deadPaint
            System.out.println("DEAD");
            throw new DeadSnakeException();
        }
        cells.add(0, newPoint);

        prevDirection = direction;
    }

    public Point getHead() {
        return cells.get(0);
    }

    public Point getTail() {
        return cells.get(cells.size() - 1);
    }

    public ArrayList<Point> getCells() {
        return cells;
    }

    public void setUp() {
        if (!prevDirection.equals(directionEnum.DOWN)) {
            direction = directionEnum.UP;
            movingVector = new Point(0, -1);
        }
    }

    public void setRight() {
        if (!prevDirection.equals(directionEnum.LEFT)) {
            direction = directionEnum.RIGHT;
            movingVector = new Point(1, 0);
        }
    }

    public void setDown() {
        if (!prevDirection.equals(directionEnum.UP)) {
            direction = directionEnum.DOWN;
            movingVector = new Point(0, 1);
        }
    }

    public void setLeft() {
        if (!prevDirection.equals(directionEnum.RIGHT)) {
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
