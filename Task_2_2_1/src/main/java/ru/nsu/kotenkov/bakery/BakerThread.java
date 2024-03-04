package ru.nsu.kotenkov.bakery;


import ru.nsu.kotenkov.bakery.exceptions.BakerInterrupted;

import javax.sound.midi.SysexMessage;


public class BakerThread extends Thread implements Staff {
    private Thread myself;
    public final int id;
    private final int efficiency;
    private boolean ready = true;
    private Order order;
    private Storage storage;

    public BakerThread(int id, int efficiency, Storage storage) {
        this.id = id;
        this.efficiency = efficiency;
        this.storage = storage;
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

        if (storage.canStore()) {
            storage.addOrder(order);
        } else {
            System.out.println("No space in the storage so I'll eat this Big Kahuna Burger");
        }
        System.out.println("Baker " + this.id + " stored order " + order.getId());
        this.ready = true;
    }
}
