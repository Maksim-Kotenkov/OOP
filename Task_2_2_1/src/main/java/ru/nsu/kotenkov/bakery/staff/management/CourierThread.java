package ru.nsu.kotenkov.bakery.staff.management;

import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.Staff;


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
    public void run() {
        try {
            this.ready = false;
            System.out.println("COURIER: Courier " + id + " took the order " + order.getId() + " with the time to deliver: " + order.getTimeToDeliver());
            Thread.sleep((order.getTimeToDeliver() / this.speed) * 1000L);
            System.out.println("COURIER: Courier " + id + " has delivered order " + order.getId());
            this.ready = true;
        } catch (InterruptedException e) {
            System.err.println("COURIER: Courier " + id + " was interrupted while delivering.");
        }
    }
}
