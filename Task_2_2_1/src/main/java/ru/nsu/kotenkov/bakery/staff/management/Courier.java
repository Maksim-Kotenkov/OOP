package ru.nsu.kotenkov.bakery.staff.management;


/**
 * A class to deserialize couriers into.
 * Then it will be converted to CourierThread.
 */
public class Courier {
    public int id;
    public int capacity;
    public int speed;

    /**
     * Setter for deserializing capacity.
     *
     * @param capacity set the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Setter for deserializing speed.
     *
     * @param speed set the speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
