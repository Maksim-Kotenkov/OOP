package ru.nsu.kotenkov.bakery;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.kotenkov.bakery.kitchen.Baker;
import ru.nsu.kotenkov.bakery.kitchen.BakerThread;
import ru.nsu.kotenkov.bakery.kitchen.KitchenManager;
import ru.nsu.kotenkov.bakery.management.Courier;
import ru.nsu.kotenkov.bakery.management.CourierThread;
import ru.nsu.kotenkov.bakery.management.DeliveryManager;
import ru.nsu.kotenkov.bakery.management.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;


public class Bakery extends Thread {
    private final ArrayList<CourierThread> couriers;
    private KitchenManager kitchen;
    private DeliveryManager delivery;


    public Bakery(ArrayList<Order> orders) {
        ObjectMapper mapper = new ObjectMapper();
        File json = Paths.get("config.json").toFile();
        ConfigMap map = null;

        try {
            map = mapper.readValue(json, ConfigMap.class);
        } catch (IOException e) {
            System.err.println("Cannot read the configuration: " + e.getMessage());
        }

        assert map != null;
        Storage storage = new Storage(map.getStorage());
        ArrayList<BakerThread> bakerThreads = new ArrayList<>();
        ArrayList<CourierThread> courierThreads = new ArrayList<>();

        for (Baker b : map.getBakers()) {
            bakerThreads.add(new BakerThread(b.id, b.efficiency, storage));
        }
        this.couriers = new ArrayList<>();
        for (Courier c : map.getCouriers()) {
            courierThreads.add(new CourierThread(c.id, c.capacity, c.speed, storage));
        }

        this.kitchen = new KitchenManager(bakerThreads, orders);
        this.delivery = new DeliveryManager(courierThreads, storage);
    }

    @Override
    public void run() {
        System.out.println("Bakery opened");
        Thread kitchenThread = new Thread(this.kitchen);
        kitchenThread.start();

        try {
            kitchenThread.join();
        } catch (InterruptedException e) {
            System.err.println("Kitchen thread interrupted: " + e.getMessage());
        }
        System.out.println("Bakery finished the day");
    }
}
