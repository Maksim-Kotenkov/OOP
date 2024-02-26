package ru.nsu.kotenkov.bakery;


import ru.nsu.kotenkov.bakery.exceptions.CourierInterrupted;


public class Courier extends Thread {
    private final int capacity;
    private boolean ready;
    private int orderShippingDuration;
    private final int speed;

    public Courier(int capacity, int speed) {
        this.capacity = capacity;
        this.speed = speed;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isReady() {
        return ready;
    }

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
