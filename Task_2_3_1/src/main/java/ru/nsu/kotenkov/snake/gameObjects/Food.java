package ru.nsu.kotenkov.snake.gameObjects;


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


public class Food {
    private final ArrayList<Point> points;

    public Food() {
        points = new ArrayList<>();
        points.add(new Point(5, 5));
    }

    public Point randomPoint(Point head) {
        Random random = new Random();
        Point point;

        do {
            point = new Point(random.nextInt(Playground.nCellsWidth),
                    random.nextInt(Playground.nCellsHeight));
        } while (point.equals(head));

        return point;
    }

    public boolean checkEaten(Point head) {
        Point toRemove;
        if (points.stream().anyMatch(head::equals)) {
            toRemove = points.stream().filter(head::equals).toList().get(0);
            points.remove(toRemove);
            points.add(randomPoint(head));
            return true;
        }

        return false;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
