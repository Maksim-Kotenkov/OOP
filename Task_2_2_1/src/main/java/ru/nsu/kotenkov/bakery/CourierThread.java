package ru.nsu.kotenkov.bakery;

import ru.nsu.kotenkov.bakery.exceptions.CourierInterrupted;

public class CourierThread extends Thread implements Staff {
    private Thread myself;
    public int id;
    private final int capacity;
    private boolean ready = true;
    private int orderShippingDuration;
    private int speed;

    public CourierThread (int id, int capacity, int orderShippingDuration) {
        this.id = id;
        this.capacity = capacity;
        this.orderShippingDuration = orderShippingDuration;
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

    public void setOrderShippingDuration(int orderShippingDuration) {
        this.orderShippingDuration = orderShippingDuration;
    }

    @Override
    public synchronized void run() {
        this.ready = false;
        try {
            Thread.sleep(this.orderShippingDuration / this.speed);
        } catch (InterruptedException e) {
            throw new CourierInterrupted("Baker " + this + " was interrupted while cooking.\n");
        }
        this.ready = true;
        this.orderShippingDuration = -1;
    }
}
