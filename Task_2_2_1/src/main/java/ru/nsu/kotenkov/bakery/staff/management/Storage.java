package ru.nsu.kotenkov.bakery.staff.management;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * A class for storing ready orders and orders to be done.
 * Custom lock flags for interactions with bakers and couriers down there.
 */
public class Storage {
    private int freeSpace;
    private ArrayList<Order> storage;
    private ArrayList<Order> orders;
    private volatile boolean customBakersLock = false;
    private volatile boolean customCouriersLock = false;

    /**
     * Constructor.
     *
     * @param freeSpace how many Big Kahuna Burgers can be stored in there
     */
    public Storage(int freeSpace) {
        this.storage = new ArrayList<>();
        this.orders = new ArrayList<>();
        if (freeSpace >= 0) {
            this.freeSpace = freeSpace;
        } else {
            this.freeSpace = 0;
            System.err.println("STORAGE: Trying to init less than "
                    + "0 capacity storage, capacity set to 0");
        }
    }

    /**
     * To set orders for the day.
     *
     * @param orders let's begin making burgers
     */
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    /**
     * Get orders that are left to serialize them.
     *
     * @return current orders
     */
    public ArrayList<Order> saveOrders() {
        return orders;
    }

//    /**
//     * Method for baker to get order for him.
//     * It handles concurrency by itself with the use of flag customBakerLock.
//     * As we cannot use original locks, this is kinda replacement.
//     *
//     * @return order to be done
//     */
//    public synchronized Order getOrder() {
//        while (customBakersLock) {
//            Thread.onSpinWait();
//        }
//        customBakersLock = true;
//
//        Order toReturn = orders.get(0);
//        orders = new ArrayList<>(orders.subList(1, orders.size()));
//        System.out.println("STORAGE: baker took order " + toReturn.getId());
//        customBakersLock = false;
//
//        return toReturn;
//    }

    /**
     * Method for baker to get order for him.
     * We lock on orders monitor and wait for the notification if there is no orders.
     *
     * @return order to be done
     */
    public Order getOrder() throws InterruptedException {
        synchronized (orders) {
            while (!anyOrders()) {
                wait();
            }
            Order toReturn = orders.get(0);
            orders = new ArrayList<>(orders.subList(1, orders.size()));
            System.out.println("STORAGE: baker took order " + toReturn.getId());

            return toReturn;
        }
    }

//    /**
//     * If we've got a new order we can add it with this method.
//     *
//     * @param order new order
//     */
//    public synchronized void addOrder(Order order) {
//        while (customBakersLock) {
//            Thread.onSpinWait();
//        }
//        customBakersLock = true;
//
//        orders.add(order);
//        freeSpace -= 1;
//        System.out.println("STORAGE: added order " + order.getId());
//        customBakersLock = false;
//    }

    /**
     * If we've got a new order we can add it with this method.
     * After that we notify bakers, that are locked in a getOrder call.
     *
     * @param order new order
     */
    public void addOrder(Order order) {
        synchronized (orders) {
            orders.add(order);
            freeSpace -= 1;
            System.out.println("STORAGE: added order " + order.getId());
            notify();
        }
    }

    /**
     * Checking free space.
     *
     * @return true = let's store, false = wait
     */
    public synchronized boolean canStore() {
        while (customCouriersLock) {
            Thread.onSpinWait();
        }

        try {
            customCouriersLock = true;
            return freeSpace != 0;
        } finally {
            customCouriersLock = false;
        }
    }

    /**
     * Adding new order to the storage.
     * Decreasing freeSpace.
     *
     * @param order new order
     */
    public synchronized void addToStorage(Order order) {
        while (customCouriersLock) {
            Thread.onSpinWait();
        }

        customCouriersLock = true;
        freeSpace -= 1;
        System.out.println("STORAGE: added to storage order " + order.getId());
        storage.add(order);
        customCouriersLock = false;
    }

    /**
     * Get order from the storage.
     * Increasing free space and removing the order from the list.
     *
     * @return the order
     */
    public synchronized Order getFromStorage() {
        while (customCouriersLock) {
            Thread.onSpinWait();
        }
        if (storage.isEmpty()) {
            return null;
        }

        try {
            customCouriersLock = true;

            Order toReturn = storage.get(0);
            storage = new ArrayList<>(storage.subList(1, storage.size()));
            freeSpace += 1;
            System.out.println("STORAGE: removed from storage order " + toReturn.getId());
            return toReturn;
        } finally {
            customCouriersLock = false;
        }
    }

    /**
     * Flag for couriers.
     *
     * @return true = get the order, false = wait
     */
    public synchronized boolean notEmpty() {
        return !storage.isEmpty();
    }

    /**
     * Flag for bakers.
     *
     * @return true = get the order, false = wait
     */
    public synchronized boolean anyOrders() {
        return !orders.isEmpty();
    }
}
