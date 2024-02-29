package ru.nsu.kotenkov.bakery;


import ru.nsu.kotenkov.bakery.exceptions.BakerInterrupted;


public class Baker implements Runnable {
    private int efficiency;
    private boolean ready;
    private int orderCookingDuration;


    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setOrderCookingDuration(int orderCookingDuration) {
        this.orderCookingDuration = orderCookingDuration;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public synchronized void run() {
        this.ready = false;
        try {
            Thread.sleep(this.orderCookingDuration / this.efficiency);
        } catch (InterruptedException e) {
            throw new BakerInterrupted("Baker " + this + " was interrupted while cooking.\n");
        }
        this.ready = true;
    }
}
