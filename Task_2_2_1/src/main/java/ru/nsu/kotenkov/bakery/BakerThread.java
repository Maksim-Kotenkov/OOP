package ru.nsu.kotenkov.bakery;

import ru.nsu.kotenkov.bakery.exceptions.BakerInterrupted;

public class BakerThread extends Thread {
    // TODO add myself
    public int id;
    private int efficiency;
    volatile boolean ready = false;
    private int orderId;
    private int orderCookingDuration;

    public BakerThread(int id, int efficiency) {
        this.id = id;
        this.efficiency = efficiency;
    }

    public void setOrderId(int id) {
        this.id = id;
    }

    public void setOrderCookingDuration(int orderCookingDuration) {
        this.orderCookingDuration = orderCookingDuration;
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public void run() {
        this.ready = false;
        System.out.println("Baker " + this.id + " started to cook order " + this.orderId);
        try {
            Thread.sleep(this.orderCookingDuration / this.efficiency);
        } catch (InterruptedException e) {
            throw new BakerInterrupted("Baker " + this + " was interrupted while cooking.\n");
        }
        System.out.println("Baker " + this.id + " finished cooking order " + this.orderId);
        this.ready = true;
    }
}
