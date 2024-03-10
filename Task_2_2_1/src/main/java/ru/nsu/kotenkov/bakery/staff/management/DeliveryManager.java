package ru.nsu.kotenkov.bakery.staff.management;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class DeliveryManager extends Thread {
    private final Storage storage;
    private ArrayList<CourierThread> couriers;
    public boolean couriersSurvivingOutside = true;

    public DeliveryManager(ArrayList<CourierThread> couriers, Storage storage) {
        this.couriers = couriers;
        this.storage = storage;
    }

    @Override
    public void run() {
        while (true) {
            if (interrupted()) {
                synchronized (this) {
                    System.out.println("DELIVERY: Waiting for all the couriers to finish the work");
                    try {
                        for (CourierThread c : couriers) {
                            if (c.getMyself() != null) {
                                c.getMyself().join();
                            }
                        }
                    } catch (InterruptedException e) {
                        System.err.println("DELIVERY: Waiting for all the couriers was interrupted");
                    }
                    couriersSurvivingOutside = false;
                    notify();
                }
                break;
            }

            synchronized (storage) {
                if (storage.notInteracting() && storage.notEmpty()) {
                    storage.setInteracting(true);
                    CourierThread readyCourier = couriers.stream().filter(CourierThread::isReady).findAny().orElse(null);
                    if (readyCourier != null) {
                        readyCourier.setOrder(storage.getOrder());
                        readyCourier.setReady(FALSE);

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
