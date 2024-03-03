package ru.nsu.kotenkov.bakery;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class Bakery extends Thread {
    private final ArrayList<BakerThread> bakers;
    private final ArrayList<CourierThread> couriers;
    private final ArrayList<Order> orders;


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
        this.bakers = new ArrayList<>();
        for (Baker b : map.getBakers()) {
            this.bakers.add(new BakerThread(b.id, b.efficiency));
        }
        this.couriers = new ArrayList<>();
        for (Courier c : map.getCouriers()) {
            this.couriers.add(new CourierThread(c.id, c.capacity, c.speed));
        }
        this.orders = orders;
    }

    @Override
    public void run() {
        System.out.println("Bakery opened");
        System.out.println("Orders number:" + orders.size());

        int bakeId = 0;

        // TODO let bakers and couriers know itself
        ArrayList<Thread> bakerThreads = new ArrayList<>();
        for (BakerThread b : bakers) {
            bakerThreads.add(new Thread(b));
        }
        ArrayList<Thread> courierThreads = new ArrayList<>();
        for (CourierThread c : couriers) {
            bakerThreads.add(new Thread(c));
        }

        // TODO check baker and start itself
        while (bakeId < orders.size()) {
            // give some work for all ready bakers
            while (bakers.stream().anyMatch(BakerThread::isReady) && bakeId < orders.size()){
                BakerThread readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null);
                readyBaker.setOrderId(bakeId);
                readyBaker.setOrderCookingDuration(orders.get(bakeId).getTimeToCook());
                readyBaker.myself.start();

                bakeId += 1;
            }

        }

        for (Thread t : bakerThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Baker interrupted");
            }
        }
        System.out.println("Bakery finished the day");
    }
}
