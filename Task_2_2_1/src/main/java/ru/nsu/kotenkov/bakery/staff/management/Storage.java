package ru.nsu.kotenkov.bakery.staff.management;

import ru.nsu.kotenkov.bakery.staff.Order;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private AtomicInteger freeSpace;
    private ArrayList<Order> storage;

    public Storage(int freeSpace) {
        this.storage = new ArrayList<>();
        if (freeSpace >= 0) {
            this.freeSpace = new AtomicInteger(freeSpace);
        } else {
            this.freeSpace = new AtomicInteger(0);
            System.err.println("STORAGE: Trying to init less than 0 capacity storage, capacity set to 0");
        }
    }

    public boolean canStore() {
        return !freeSpace.compareAndSet(0, 0);
    }

    public void addOrder(Order order) {
        freeSpace = new AtomicInteger(freeSpace.intValue() - 1);
        System.out.println("STORAGE: add order " + order.getId());
        storage.add(order);
    }

    public Order getOrder() {
        Order toReturn = storage.get(0);
        storage = new ArrayList<Order>(storage.subList(1, storage.size()));
        freeSpace = new AtomicInteger(freeSpace.intValue() + 1);
        System.out.println("STORAGE: remove order " + toReturn.getId());
        return toReturn;
    }

    public synchronized boolean notEmpty() {
        return !storage.isEmpty();
    }
}
