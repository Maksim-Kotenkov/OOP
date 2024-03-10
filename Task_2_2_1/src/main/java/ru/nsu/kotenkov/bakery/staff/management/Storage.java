package ru.nsu.kotenkov.bakery.staff.management;

import ru.nsu.kotenkov.bakery.staff.Order;

import java.util.ArrayList;

public class Storage {
    private int freeSpace;
    private ArrayList<Order> storage;
    private boolean interacting = false;


    public Storage(int freeSpace) {
        this.storage = new ArrayList<>();
        if (freeSpace >= 0) {
            this.freeSpace = freeSpace;
        } else {
            this.freeSpace = 0;
            System.err.println("STORAGE: Trying to init less than 0 capacity storage, capacity set to 0");
        }
    }

    public boolean canStore() {
        return freeSpace != 0;
    }

    public void addOrder(Order order) {
        freeSpace -= 1;
        System.out.println("STORAGE: add order " + order.getId());
        storage.add(order);
    }

    public Order getOrder() {
        Order toReturn = storage.get(0);
        storage = new ArrayList<Order>(storage.subList(1, storage.size()));
        freeSpace += 1;
        System.out.println("STORAGE: remove order " + toReturn.getId());
        return toReturn;
    }

    public synchronized boolean notEmpty() {
        return !storage.isEmpty();
    }

    public void setInteracting(boolean interacting) {
        this.interacting = interacting;
    }

    public boolean notInteracting() {
        return !interacting;
    }
}
