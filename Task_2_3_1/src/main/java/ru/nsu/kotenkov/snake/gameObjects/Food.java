package ru.nsu.kotenkov.snake.gameObjects;


import java.awt.Point;
import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.Random;


public class Food {
    private ArrayList<Point> points;
    private final Playground playground;

    public Food(ArrayList<Point> snakePoints, Playground playground) {
        this.playground = playground;
        points = new ArrayList<>();
        for (int i = 0; i < playground.foodNumber; i++) {
            points.add(randomPoint(snakePoints));
        }
    }

    public void reInitFood(ArrayList<Point> snakePoints) {
        this.points = new ArrayList<>();
        for (int i = 0; i < this.playground.foodNumber; i++) {
            this.points.add(randomPoint(snakePoints));
        }
    }

    public Point randomPoint(ArrayList<Point> snakePoints) {
        Random random = new Random();
        Point point;

        do {
            point = new Point(random.nextInt(playground.nCellsWidth),
                    random.nextInt(playground.nCellsHeight));
        } while (points.contains(point) || snakePoints.stream().anyMatch(point::equals));

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
