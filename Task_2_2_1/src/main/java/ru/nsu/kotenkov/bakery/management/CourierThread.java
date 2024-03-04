package ru.nsu.kotenkov.bakery.management;

import ru.nsu.kotenkov.bakery.Order;
import ru.nsu.kotenkov.bakery.Staff;
import ru.nsu.kotenkov.bakery.exceptions.CourierInterrupted;
import ru.nsu.kotenkov.bakery.management.Storage;

public class CourierThread extends Thread implements Staff {
    private Thread myself;
    public int id;
    private final int capacity;
    private int speed = 1;
    private boolean ready = true;
    private Order order;

    public CourierThread (int id, int capacity, int speed) {
        this.id = id;
        this.capacity = capacity;
        this.speed = speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    public Thread getMyself() {
        return myself;
    }

    public void setMyself(Thread myself) {
        this.myself = myself;
    }

    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public synchronized void run() {
        this.ready = false;
        System.out.println("COURIER: Courier " + id + " took the order " + order.getId() + " with the time to deliver: " + order.getTimeToDeliver());
        try {
            Thread.sleep(order.getTimeToDeliver() / this.speed);
        } catch (InterruptedException e) {
            throw new CourierInterrupted("Courier " + id + " was interrupted while delivering.\n");
        }
        System.out.println("COURIER: Courier " + id + " has delivered order " + order.getId());
        this.ready = true;
    }
}
