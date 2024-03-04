package ru.nsu.kotenkov.bakery.management;

import java.util.ArrayList;

public class DeliveryManager extends Thread {
    private Storage storage;
    private ArrayList<CourierThread> couriers;

    public DeliveryManager(ArrayList<CourierThread> couriers, Storage storage) {
        this.couriers = couriers;
        this.storage = storage;
    }

    @Override
    public void run() {

    }
}
