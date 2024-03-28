package ru.nsu.kotenkov.snake.gameobjects;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


/**
 * Food class.
 */
public class Food {
    private ArrayList<Point> points;
    private final Playground playground;

    /**
     * Constructor.
     *
     * @param snakePoints not to place fruits in the snake
     * @param playground to get number of fruits at the same time on the field
     */
    public Food(ArrayList<Point> snakePoints, Playground playground) {
        this.playground = playground;
        points = new ArrayList<>();
        for (int i = 0; i < playground.foodNumber; i++) {
            points.add(randomPoint(snakePoints));
        }
    }

    /**
     * On reset or victory.
     *
     * @param snakePoints no to place fruits at in the snake
     */
    public void reInitFood(ArrayList<Point> snakePoints) {
        this.points = new ArrayList<>();
        for (int i = 0; i < this.playground.foodNumber; i++) {
            this.points.add(randomPoint(snakePoints));
        }
    }

    /**
     * Get new random point without overlapping with snake and other fruits.
     *
     * @param snakePoints snake points
     * @return new point
     */
    public Point randomPoint(ArrayList<Point> snakePoints) {
        Random random = new Random();
        Point point;

        do {
            point = new Point(random.nextInt(playground.numberCellsWidth),
                    random.nextInt(playground.numberCellsHeight));
        } while (points.contains(point) || snakePoints.stream().anyMatch(point::equals));

        return point;
    }

    /**
     * Eaten condition.
     *
     * @param snakePoints not only the head because we then place new food and need all the snake
     * @return yes/no
     */
    public boolean checkEaten(ArrayList<Point> snakePoints) {
        Point toRemove;
        if (points.stream().anyMatch(snakePoints.get(0)::equals)) {
            toRemove = points.stream().filter(snakePoints.get(0)::equals).toList().get(0);
            points.remove(toRemove);
            points.add(randomPoint(snakePoints));
            return true;
        }

        return false;
    }

    /**
     * For printing.
     *
     * @return fruits
     */
    public ArrayList<Point> getPoints() {
        return points;
    }
}
