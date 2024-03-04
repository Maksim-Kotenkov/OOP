package ru.nsu.kotenkov.bakery.management;

import ru.nsu.kotenkov.bakery.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Storage {
    AtomicInteger freeSpace;
    ArrayList<Order> storage;

    public Storage(int freeSpace) {
        this.storage = new ArrayList<>();
        this.freeSpace = new AtomicInteger(freeSpace);
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
