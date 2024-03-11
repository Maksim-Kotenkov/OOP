package ru.nsu.kotenkov.bakery;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.configuring.BakeryConfig;
import ru.nsu.kotenkov.bakery.staff.kitchen.KitchenManager;
import ru.nsu.kotenkov.bakery.staff.management.DeliveryManager;


/**
 * Bakery class, that is the OFFICE.
 * It starts kitchen and management, and interrupts their threads when the time is out.
 * Configuration can be taken from special classes.
 */
public class Bakery extends Thread {
    private final KitchenManager kitchen;
    private final DeliveryManager delivery;
    private int workingHours;


    /**
     * Constructor with configs from special classes.
     *
     * @param orders we just give the orders here
     */
    public Bakery(ArrayList<Order> orders) {
        BakeryConfig config = new BakeryConfig();

        this.kitchen = new KitchenManager(config.getBakerThreads(), orders);
        this.delivery = new DeliveryManager(config.getCourierThreads(), config.getStorage());
        this.workingHours = config.getWorkingHours();
    }

    /**
     * Setter to test bakery well.
     *
     * @param workingHours new value
     */
    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    /**
     * Body of a bakery thread.
     * Start kitchen and management threads.
     * Wait till the end of the day, let them do their work without our control.
     * At the end of the day interrupt them and wait for the notification, that they are finished.
     */
    @Override
    public void run() {
        System.out.println("OFFICE: Bakery opened");
        Thread kitchenThread = new Thread(this.kitchen);
        Thread managingThread = new Thread(this.delivery);
        kitchenThread.start();
        managingThread.start();

        try {
            sleep(workingHours * 1000L);
        } catch (InterruptedException e) {
            System.err.println("OFFICE: Kitchen thread was interrupted during the work");
        }

        System.out.println("\nOFFICE: STOPPING THE WORK\n");

        // The end of the day
        synchronized (kitchen) {
            if (kitchen.bakersWorkingHard) {
                kitchenThread.interrupt();
                try {
                    while (kitchen.bakersWorkingHard) {
                        kitchen.wait();
                    }
                } catch (InterruptedException e) {
                    System.err.println("OFFICE: Workers interrupted "
                            + "and the office is on fire...");
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
                    System.err.println("OFFICE: Couriers didn't come back... "
                            + "The may be dead by now...");
                }
            }
            System.out.println("OFFICE: Delivery finished the day");
        }

        System.out.println("OFFICE: Office finished the day");
    }
}
