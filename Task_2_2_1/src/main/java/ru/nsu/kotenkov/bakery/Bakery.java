package ru.nsu.kotenkov.bakery;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.configuring.BakeryConfig;
import ru.nsu.kotenkov.bakery.staff.configuring.PreviousOrders;
import ru.nsu.kotenkov.bakery.staff.kitchen.BakerThread;
import ru.nsu.kotenkov.bakery.staff.management.CourierThread;
import ru.nsu.kotenkov.bakery.staff.management.Storage;


/**
 * Bakery class, that is the OFFICE.
 * It starts kitchen and management, and interrupts their threads when the time is out.
 * Configuration can be taken from special classes.
 */
public class Bakery {
    private final ArrayList<CourierThread> couriers;
    private final ArrayList<BakerThread> bakers;
    private final Storage storage;
    private final ArrayList<Order> orders;
    private int workingHours;


    /**
     * Constructor with configs from special classes.
     *
     * @param orders we just give the orders here
     */
    public Bakery(ArrayList<Order> orders) {
        BakeryConfig config = new BakeryConfig();

        this.orders = orders;
        this.couriers = config.getCourierThreads();
        this.bakers = config.getBakerThreads();
        this.storage = config.getStorage();
        this.workingHours = config.getWorkingHours();
    }

    /**
     * Body of a bakery thread.
     * Start kitchen and management threads.
     * Wait till the end of the day, let them do their work without our control.
     * At the end of the day interrupt them and wait for the notification, that they are finished.
     */
    public void run() {
        System.out.println("BAKERY: Bakery opened");
        System.out.println("Orders number:" + orders.size());

        storage.setOrders(orders);

        for (BakerThread b : bakers) {
            b.start();
        }
        for (CourierThread c : couriers) {
            c.start();
        }

        try {
            Thread.sleep(workingHours * 1000L);
        } catch (InterruptedException e) {
            System.err.println("BAKERY: Office thread was interrupted during the work");
        }

        System.out.println("\nBAKERY: STOPPING THE WORK\n");

        // The end of the day
        for (BakerThread b : bakers) {
            b.interrupt();
        }
        PreviousOrders.store(storage.saveOrders());
        System.out.println("BAKERY: Kitchen finished the day");

        for (CourierThread c : couriers) {
            c.interrupt();
        }
        System.out.println("BAKERY: Delivery finished the day");

        System.out.println("BAKERY: See ya tomorrow");
    }
}
