package ru.nsu.kotenkov.bakery.management;


import ru.nsu.kotenkov.bakery.exceptions.CourierInterrupted;


public class Courier {
    public int id;
    public int capacity;
    public int speed;

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
