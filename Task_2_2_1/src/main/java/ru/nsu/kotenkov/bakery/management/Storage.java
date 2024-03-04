package ru.nsu.kotenkov.bakery.management;

import ru.nsu.kotenkov.bakery.Order;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    AtomicInteger freeSpace;
    ArrayList<Order> storage;

    public Storage(int freeSpace) {
        this.storage = new ArrayList<>();
        this.freeSpace = new AtomicInteger(freeSpace);
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = new AtomicInteger(freeSpace);
    }

    public boolean canStore() {
        return !freeSpace.compareAndSet(0, 0);
    }

    public void addOrder(Order order) {
        freeSpace = new AtomicInteger(freeSpace.intValue() - 1);
        System.out.println("Storage freeSpace now is: " + freeSpace.intValue());
        storage.add(order);
    }

    public ArrayList<Order> getStorage() {
        return storage;
    }
}
