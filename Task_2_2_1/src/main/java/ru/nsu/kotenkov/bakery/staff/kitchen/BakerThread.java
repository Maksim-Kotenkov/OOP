package ru.nsu.kotenkov.bakery.staff.kitchen;


import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.Staff;
import ru.nsu.kotenkov.bakery.staff.management.Storage;


/**
 * A class that is a Baker, but better.
 * It can be a thread.
 */
public class BakerThread extends Thread implements Staff {
    private Thread myself;
    public final int id;
    private final int efficiency;
    private boolean ready = true;
    private Order order;
    private final Storage storage;

    /**
     * Nothing special.
     * Everything is from deserialized Baker.
     *
     * @param id id
     * @param efficiency eff
     * @param storage st
     */
    public BakerThread(int id, int efficiency, Storage storage) {
        this.id = id;
        this.efficiency = efficiency;
        this.storage = storage;
    }

    /**
     * Setter to know Thread is connected to this object.
     *
     * @param myself connected thread
     */
    public void setMyself(Thread myself) {
        this.myself = myself;
    }

    /**
     * Before the start og a thread we need to define what order should we produce.
     *
     * @param order the order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Not to start a thread twice we better switch state by the caller, not in run.
     *
     * @param ready new state
     */
    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * Checker for the state.
     *
     * @return the current state
     */
    @Override
    public boolean isReady() {
        return ready;
    }

    /**
     * Getter for the connected thread.
     *
     * @return what am I
     */
    public Thread getMyself() {
        return myself;
    }

    /**
     * Body of this baker thread.
     */
    @Override
    public void run() {
        try {
            this.ready = false;
            System.out.println("BAKER: Baker " + this.id
                    + " started cooking order " + order.getId());
            Thread.sleep((order.getTimeToCook() / this.efficiency) * 1000L);
            System.out.println("BAKER: Baker " + this.id
                    + " finished cooking order " + order.getId());
            while (true) {
                if (storage.canStore()) {
                    storage.addOrder(order);
                    System.out.println("BAKER: Baker " + this.id
                            + " stored order " + order.getId());
                    break;
                }
            }
            this.ready = true;
        } catch (InterruptedException e) {
            System.err.println("BAKER: Baker " + id + " was interrupted while cooking.");
        }
    }
}
