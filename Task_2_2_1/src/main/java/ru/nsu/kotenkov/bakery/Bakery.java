package ru.nsu.kotenkov.bakery;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;


public class Bakery extends Thread {
    private final ArrayList<BakerThread> bakers;
    private final ArrayList<CourierThread> couriers;
    private final ArrayList<Order> orders;
    private Storage storage;


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
        this.storage = new Storage(map.getStorage());
        for (Baker b : map.getBakers()) {
            this.bakers.add(new BakerThread(b.id, b.efficiency, this.storage));
        }
        this.couriers = new ArrayList<>();
        for (Courier c : map.getCouriers()) {
            this.couriers.add(new CourierThread(c.id, c.capacity, c.speed, this.storage));
        }
        this.orders = orders;
    }

    @Override
    public void run() {
        System.out.println("Bakery opened");
        System.out.println("Orders number:" + orders.size());

        int bakeId = 0;

//        ArrayList<Thread> bakerThreads = new ArrayList<>();
        for (BakerThread b : bakers) {
            b.setMyself(new Thread(b));
        }
//        ArrayList<Thread> courierThreads = new ArrayList<>();
        for (CourierThread c : couriers) {
            c.setMyself(new Thread(c));
        }

        while (bakeId < orders.size()) {
            // give some work for all ready bakers
            BakerThread readyBaker = null;
            while ((readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null)) != null
                    && bakeId < orders.size()){
//                BakerThread readyBaker = bakers.stream().filter(BakerThread::isReady).findAny().orElse(null);
                readyBaker.setOrder(orders.get(bakeId));
                readyBaker.setMyself(new Thread(readyBaker));
                readyBaker.setReady(FALSE);
                readyBaker.getMyself().start();

                bakeId += 1;
            }

        }

        for (Thread t : bakers.stream().map(BakerThread::getMyself).collect(Collectors.toSet())) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Baker interrupted");
            }
        }
        System.out.println("Bakery finished the day");
    }
}
