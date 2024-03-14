package ru.nsu.kotenkov.bakery.staff.management;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * Runnable thread-like class for couriers.
 */
public class CourierThread extends Thread {
    public int id;
    private final int capacity;
    private final int speed;
    private final ArrayList<Order> orders;
    private final Storage storage;

    /**
     * Constructor.
     *
     * @param id id of a courier
     * @param capacity how many Big Kahuna Burgers can be delivered
     * @param speed fast boi
     */
    public CourierThread(int id, int capacity, int speed, Storage storage) {
        this.id = id;
        this.capacity = capacity;
        this.speed = speed;
        this.orders = new ArrayList<>();
        this.storage = storage;
    }

    /**
     * Giving an order.
     *
     * @param order new order
     */
    public void setOrder(Order order) {
        this.orders.add(order);
        System.out.println("COURIER: Courier " + id + " took the order "
                + order.getId() + " with the time to deliver: " + order.getTimeToDeliver());
    }

    /**
     * Thread body that is run while delivering.
     */
    @Override
    public void run() {
        try {
            Order order;
            while (true) {
                if (isInterrupted()) {
                    System.err.println("COURIER: Courier " + id
                            + " coming home");
                    return;
                }

                synchronized (storage) {
                    order = storage.getFromStorage();
                }

                if (order == null) {
                    continue;
                }

                System.out.println("COURIER: Courier " + id + " took the order "
                        + order.getId() + " with the time to deliver: " + order.getTimeToDeliver());

                Thread.sleep((order.getTimeToDeliver() / this.speed) * 1000L);

                System.out.println("COURIER: Courier " + id
                        + " has delivered order " + order.getId());
            }
        } catch (InterruptedException e) {
            System.err.println("COURIER: Courier " + id + " was interrupted while delivering.");
        }
    }
}
