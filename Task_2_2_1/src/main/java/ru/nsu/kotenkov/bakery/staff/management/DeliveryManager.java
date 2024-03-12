package ru.nsu.kotenkov.bakery.staff.management;


import java.util.ArrayList;


/**
 * Managing couriers and giving orders from the storage.
 */
public class DeliveryManager extends Thread {
    private final Storage storage;
    private final ArrayList<CourierThread> couriers;
    public boolean couriersSurvivingOutside = true;

    /**
     * Constructor.
     *
     * @param couriers list of courierThreads
     * @param storage our storage
     */
    public DeliveryManager(ArrayList<CourierThread> couriers, Storage storage) {
        this.couriers = couriers;
        this.storage = storage;
    }

    /**
     * Thread body for managing courier threads and interruptions from the office.
     * At the end of the day - waiting for all the couriers.
     */
    @Override
    public void run() {
        while (true) {
            if (interrupted()) {
                synchronized (this) {
                    System.out.println("DELIVERY: Waiting for all the "
                            + "couriers to finish the work");
                    try {
                        for (CourierThread c : couriers) {
                            if (c.getMyself() != null) {
                                c.getMyself().join();
                            }
                        }
                    } catch (InterruptedException e) {
                        System.err.println("DELIVERY: Waiting for all the "
                                + "couriers was interrupted");
                    }
                    couriersSurvivingOutside = false;
                    notify();
                }
                break;
            }

            synchronized (storage) {
                if (storage.notInteracting() && storage.notEmpty()) {
                    storage.setInteracting(true);
                    CourierThread readyCourier = couriers.stream()
                            .filter(CourierThread::isReady)
                            .findAny()
                            .orElse(null);
                    if (readyCourier != null) {
                        // Give as many orders, as courier can handle
                        while (readyCourier.canGetOrder() && storage.notEmpty()) {
                            readyCourier.setOrder(storage.getOrder());
                        }
                        readyCourier.setReady(false);

                        // every time we need a new thread
                        // (because the same thread cannot be started many times)
                        // so, CourierThread object stores what Thread was created
                        // from this CourierThread
                        readyCourier.setMyself(new Thread(readyCourier));
                        readyCourier.getMyself().start();
                    }
                    storage.setInteracting(false);
                }
            }

        }
    }
}
