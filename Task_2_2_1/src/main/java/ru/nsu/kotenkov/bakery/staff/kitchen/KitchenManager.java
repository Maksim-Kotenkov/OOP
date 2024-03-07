package ru.nsu.kotenkov.bakery.staff.kitchen;


import static java.lang.Boolean.FALSE;

import java.util.ArrayList;
import java.util.stream.Collectors;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * A class that give orders to bakers and synchronize after termination.
 */
public class KitchenManager extends Thread {
    private final ArrayList<BakerThread> bakers;
    private final ArrayList<Order> orders;
    public boolean bakersWorkingHard = true;

    /**
     * Constructor that sets bakers and orders.
     *
     * @param bakers a list of baker threads
     * @param orders a list of order threads
     */
    public KitchenManager(ArrayList<BakerThread> bakers, ArrayList<Order> orders) {
        this.bakers = bakers;
        this.orders = orders;
    }

    /**
     * A method for kitchen Thread.
     * We manage Bakers, give them orders, wait for them if we are interrupted.
     */
    @Override
    public void run() {
        System.out.println("KITCHEN: Orders number:" + orders.size());

        int bakeId = 0;

        // we try to bake all the orders while we can
        while (bakeId < orders.size()) {
            // we need to join all baker threads if we get interrupted from anything
            // (Bakery thread, system interruptions)
            if (interrupted()) {
                synchronized (this) {
                    System.out.println("KITCHEN: Waiting for all the working bakers to finish baking");
                    // TODO also store all orders that are left in json
                    // TODO create a class for saving orders
                    try {
                        for (BakerThread b : bakers) {
                            if (b.getMyself() != null) {
                                b.getMyself().join();
                            }
                        }
                    } catch (InterruptedException e) {
                        System.err.println("KITCHEN: Waiting for all the bakers was interrupted");
                    }
                    bakersWorkingHard = false;
                    notify();
                }
                break;
            }

            // give some work for all ready bakers
            BakerThread readyBaker = null;
            while ((readyBaker = bakers.stream()
                    .filter(BakerThread::isReady)
                    .findAny()
                    .orElse(null)) != null
                    && bakeId < orders.size()) {
                // start one baker with all the setup
                readyBaker.setOrder(orders.get(bakeId));
                readyBaker.setReady(FALSE);
                readyBaker.setMyself(new Thread(readyBaker));
                readyBaker.getMyself().start();

                bakeId += 1;
            }
        }

        // interruption can be caught after we have finished all orders
        for (Thread t : bakers.stream().map(BakerThread::getMyself).collect(Collectors.toSet())) {
            try {
                if (t != null) {
                    t.join();
                }
            } catch (InterruptedException e) {
                synchronized (this) {
                    System.out.println("KITCHEN: Waiting for all the working bakers to finish baking");
                    try {
                        for (BakerThread b : bakers) {
                            if (b.getMyself() != null) {
                                b.getMyself().join();
                            }
                        }
                    } catch (InterruptedException ex) {
                        System.err.println("KITCHEN: Waiting for all the bakers was interrupted");
                    }
                    bakersWorkingHard = false;
                    notify();
                }
            }
        }
    }
}
