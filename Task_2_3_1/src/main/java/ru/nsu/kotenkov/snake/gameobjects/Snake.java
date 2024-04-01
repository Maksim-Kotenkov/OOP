package ru.nsu.kotenkov.snake.gameobjects;


import java.awt.Point;
import java.util.ArrayList;
import ru.nsu.kotenkov.snake.gameobjects.exceptions.DeadSnakeException;
import ru.nsu.kotenkov.snake.fxlogic.Playground;


/**
 * Snake class.
 */
public class Snake {
    /**
     * Make enum for directions.
     */
    private enum DirectionEnum {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private final ArrayList<Point> cells;
    private final Playground playground;
    private int length = 2;
    private DirectionEnum direction = DirectionEnum.RIGHT;
    private DirectionEnum prevDirection = DirectionEnum.RIGHT;
    private Point movingVector = new Point(1, 0);
    private boolean growNextTime;

    /**
     * Initialize with length of two.
     *
     * @param playground playground parameters
     */
    public Snake(Playground playground) {
        cells = new ArrayList<>();
        cells.add(new Point(2, 2));
        cells.add(new Point(2, 3));
        this.playground = playground;
    }

    /**
     * Movement with handling screen edges and biting yourself.
     *
     * @throws DeadSnakeException is dead condition signal
     */
    public void move() throws DeadSnakeException {
        Point newPoint = new Point(cells.get(0).x + movingVector.x,
                cells.get(0).y + movingVector.y);

        if (newPoint.x < 0) {
            newPoint.x = playground.numberCellsWidth - 1;
        } else if (newPoint.x > playground.numberCellsWidth - 1) {
            newPoint.x = 0;
        }

        if (newPoint.y < 0) {
            newPoint.y = playground.numberCellsHeight - 1;
        } else if (newPoint.y > playground.numberCellsHeight - 1) {
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

    /**
     * Getter.
     *
     * @return obj
     */
    public Point getHead() {
        return cells.get(0);
    }

    /**
     * Getter.
     *
     * @return obj
     */
    public Point getTail() {
        return cells.get(cells.size() - 1);
    }

    /**
     * Getter for printing the snake.
     *
     * @return obj
     */
    public ArrayList<Point> getCells() {
        return cells;
    }

    /**
     * Direction setter.
     */
    public void setUp() {
        if (!prevDirection.equals(DirectionEnum.DOWN)) {
            direction = DirectionEnum.UP;
            movingVector = new Point(0, -1);
        }
    }

    /**
     * Direction setter.
     */
    public void setRight() {
        if (!prevDirection.equals(DirectionEnum.LEFT)) {
            direction = DirectionEnum.RIGHT;
            movingVector = new Point(1, 0);
        }
    }

    /**
     * Direction setter.
     */
    public void setDown() {
        if (!prevDirection.equals(DirectionEnum.UP)) {
            direction = DirectionEnum.DOWN;
            movingVector = new Point(0, 1);
        }
    }

    /**
     * Direction setter.
     */
    public void setLeft() {
        if (!prevDirection.equals(DirectionEnum.RIGHT)) {
            direction = DirectionEnum.LEFT;
            movingVector = new Point(-1, 0);
        }
    }

    /**
     * Fruit eaten condition.
     */
    public void growNextTime() {
        growNextTime = true;
    }

    /**
     * Getter.
     *
     * @return len
     */
    public int getLength() {
        return length;
    }
}
