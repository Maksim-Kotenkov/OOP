package ru.nsu.kotenkov.bakery.staff.management;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * A class for storing ready orders.
 * Only one thread can interact with the storage by changing interacting flag.
 */
public class Storage {
    private int freeSpace;
    private ArrayList<Order> storage;
    private boolean interacting = false;

    /**
     * Constructor.
     *
     * @param freeSpace how many Big Kahuna Burgers can be stored in there
     */
    public Storage(int freeSpace) {
        this.storage = new ArrayList<>();
        if (freeSpace >= 0) {
            this.freeSpace = freeSpace;
        } else {
            this.freeSpace = 0;
            System.err.println("STORAGE: Trying to init less than "
                    + "0 capacity storage, capacity set to 0");
        }
    }

    /**
     * Checking free space.
     *
     * @return true = let's store, false = wait
     */
    public boolean canStore() {
        return freeSpace != 0;
    }

    /**
     * Adding new order to the storage.
     * Decreasing freeSpace.
     *
     * @param order new order
     */
    public void addOrder(Order order) {
        freeSpace -= 1;
        System.out.println("STORAGE: add order " + order.getId());
        storage.add(order);
    }

    /**
     * Get order from the storage.
     * Increasing free space and removing the order from the list.
     *
     * @return the order
     */
    public Order getOrder() {
        Order toReturn = storage.get(0);
        storage = new ArrayList<>(storage.subList(1, storage.size()));
        freeSpace += 1;
        System.out.println("STORAGE: remove order " + toReturn.getId());
        return toReturn;
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
     * Setter for interacting with the storage.
     * Is set when we check freeSpace or emptiness.
     *
     * @param interacting new state
     */
    public void setInteracting(boolean interacting) {
        this.interacting = interacting;
    }

    /**
     * Can we check freeSpace or emptiness and store/get order.
     *
     * @return current state
     */
    public boolean notInteracting() {
        return !interacting;
    }
}
