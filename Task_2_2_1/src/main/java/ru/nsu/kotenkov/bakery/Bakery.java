package ru.nsu.kotenkov.bakery;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.kotenkov.bakery.configuring.BakeryConfig;
import ru.nsu.kotenkov.bakery.kitchen.Baker;
import ru.nsu.kotenkov.bakery.kitchen.BakerThread;
import ru.nsu.kotenkov.bakery.kitchen.KitchenManager;
import ru.nsu.kotenkov.bakery.management.Courier;
import ru.nsu.kotenkov.bakery.management.CourierThread;
import ru.nsu.kotenkov.bakery.management.DeliveryManager;
import ru.nsu.kotenkov.bakery.management.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Bakery extends Thread {
    private final KitchenManager kitchen;
    private final DeliveryManager delivery;
    private final int workingHours;


    public Bakery(ArrayList<Order> orders) {
        BakeryConfig config = new BakeryConfig();

        this.kitchen = new KitchenManager(config.getBakerThreads(), orders, this);
        this.delivery = new DeliveryManager(config.getCourierThreads(), config.getStorage(), this);
        this.workingHours = config.getWorkingHours();
    }

    @Override
    public void run() {
        System.out.println("OFFICE: Bakery opened");
        Thread kitchenThread = new Thread(this.kitchen);
        Thread managingThread = new Thread(this.delivery);
        this.delivery.setMyself(managingThread);
        kitchenThread.start();
        managingThread.start();

        try {
            sleep(workingHours * 1000L);
        } catch (InterruptedException e) {
            System.err.println("OFFICE: Kitchen thread interrupted: " + e.getMessage());
        }

        System.out.println("OFFICE: STOPPING THE WORK");
        // The end of the day
        synchronized (kitchen) {
            if (kitchen.bakersWorkingHard) {
                kitchenThread.interrupt();
                try {
                    while (kitchen.bakersWorkingHard) {
                        kitchen.wait();
                    }
                } catch (InterruptedException e) {
                    System.err.println("OFFICE: Workers interrupted and the office is on fire...");
                }
            }
        }
        System.out.println("OFFICE: Kitchen finished the day");

        synchronized (delivery) {
            if (delivery.couriersSurvivingOutside) {
                managingThread.interrupt();
                try {
                    while (delivery.couriersSurvivingOutside) {
                        delivery.wait();
                    }
                } catch (InterruptedException e) {
                    System.err.println("OFFICE: Couriers didn't come back... The may be dead by now...");
                }
            }
            System.out.println("OFFICE: Delivery finished the day");
        }

        System.out.println("OFFICE: Office finished the day");
    }
}
