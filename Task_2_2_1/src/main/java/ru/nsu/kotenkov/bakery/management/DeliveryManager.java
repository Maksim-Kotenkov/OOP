package ru.nsu.kotenkov.bakery.management;

import ru.nsu.kotenkov.bakery.Bakery;
import ru.nsu.kotenkov.bakery.kitchen.Baker;
import ru.nsu.kotenkov.bakery.kitchen.BakerThread;

import javax.swing.plaf.basic.BasicEditorPaneUI;
import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class DeliveryManager extends Thread {
    private Thread myself;
    private Storage storage;
    private ArrayList<CourierThread> couriers;
    private final Bakery office;
    public boolean couriersSurvivingOutside = true;

    public DeliveryManager(ArrayList<CourierThread> couriers, Storage storage, Bakery office) {
        this.couriers = couriers;
        this.storage = storage;
        this.office = office;
    }

    public void setMyself(Thread myself) {
        this.myself = myself;
    }

    @Override
    public void run() {
        while (true) {
            if (interrupted()) {
                System.out.println("DELIVERY: Waiting for all the couriers to finish the work");
                synchronized (this) {
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

            if (storage.notEmpty()) {
                CourierThread readyCourier = couriers.stream().filter(CourierThread::isReady).findAny().orElse(null);
                if (readyCourier != null) {
                    readyCourier.setOrder(storage.getOrder());
                    readyCourier.setReady(FALSE);
                    readyCourier.setMyself(new Thread(readyCourier));
                    readyCourier.getMyself().start();
                }
            }
        }
    }
}
