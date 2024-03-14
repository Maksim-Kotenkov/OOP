package ru.nsu.kotenkov.bakery.staff.kitchen;


import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.management.Storage;


/**
 * A class that is a Baker, but better.
 * It can be a thread.
 */
public class BakerThread extends Thread {
    public final int id;
    private final int efficiency;
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
     * Before the start og a thread we need to define what order should we produce.
     *
     * @param order the order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Body of this baker thread.
     */
    @Override
    public void run() {
        try {
            while (true) {
                if (isInterrupted()) {
                    System.err.println("BAKER: Baker " + id
                            + " finishing Big Kahuna burger and going home");
                    return;
                }

                synchronized (storage) {
                    if (storage.anyOrders()) {
                        order = storage.getOrder();
                    } else {
                        continue;
                    }
                }

                System.out.println("BAKER: Baker " + this.id
                        + " started cooking order " + order.getId());
                Thread.sleep((order.getTimeToCook() / this.efficiency) * 1000L);
                System.out.println("BAKER: Baker " + this.id
                        + " finished cooking order " + order.getId());

                synchronized (storage) {
                    while (true) {
                        if (storage.canStore()) {
                            storage.addToStorage(order);
                            System.out.println("BAKER: Baker " + this.id
                                    + " stored order " + order.getId());
                            break;
                        }
                }
                }
            }
        } catch (InterruptedException e) {
            System.err.println("BAKER: Baker " + id + " was interrupted while cooking.");
        }
    }
}
