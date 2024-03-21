package ru.nsu.kotenkov.snake.gameObjects;


import java.awt.Point;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.Random;


public class Food {
    private final ArrayList<Point> points;

    public Food() {
        points = new ArrayList<>();
        points.add(new Point(5, 5));
    }

    public Point randomPoint(ArrayList<Point> points) {
        Random random = new Random();
        Point point;

        do {
            point = new Point(random.nextInt(Playground.nCellsWidth),
                    random.nextInt(Playground.nCellsHeight));
        } while (points.stream().anyMatch(point::equals));

        return point;
    }

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

    public ArrayList<Point> getPoints() {
        return points;
    }
}
