package ru.nsu.kotenkov.bakery;


import ru.nsu.kotenkov.bakery.exceptions.BakerInterrupted;


public class BakerThread extends Thread implements Staff {
    private Thread myself;
    public final int id;
    private final int efficiency;
    private boolean ready = true;
    private Order order;

    public BakerThread(int id, int efficiency) {
        this.id = id;
        this.efficiency = efficiency;
    }

    public void setMyself(Thread myself) {
        this.myself = myself;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    public Thread getMyself() {
        return myself;
    }

    @Override
    public void run() {
        this.ready = false;
        System.out.println("Baker " + this.id + " started cooking order " + order.getId());
        try {
            Thread.sleep((order.getTimeToCook() / this.efficiency) * 1000L);
        } catch (InterruptedException e) {
            throw new BakerInterrupted("Baker " + this + " was interrupted while cooking.\n");
        }
        System.out.println("Baker " + this.id + " finished cooking order " + order.getId());
        this.ready = true;
    }
}
