package ru.nsu.kotenkov.bakery.staff.configuring;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.kotenkov.bakery.staff.kitchen.Baker;
import ru.nsu.kotenkov.bakery.staff.kitchen.BakerThread;
import ru.nsu.kotenkov.bakery.staff.management.Courier;
import ru.nsu.kotenkov.bakery.staff.management.CourierThread;
import ru.nsu.kotenkov.bakery.staff.management.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BakeryConfig {
    private ArrayList<BakerThread> bakerThreads = null;
    private ArrayList<CourierThread> courierThreads = null;
    private Storage storage = null;
    private int workingHours = -1;

    public BakeryConfig() {
        ObjectMapper mapper = new ObjectMapper();
        File json = Paths.get("config.json").toFile();
        ConfigMap map = null;

        try {
            map = mapper.readValue(json, ConfigMap.class);
        } catch (IOException e) {
            System.err.println("Cannot read the configuration: " + e.getMessage());
        }

        assert map != null;
        this.storage = new Storage(map.getStorage());
        this.bakerThreads = new ArrayList<>();
        this.courierThreads = new ArrayList<>();

        for (Baker b : map.getBakers()) {
            bakerThreads.add(new BakerThread(b.id, b.efficiency, storage));
        }

        for (Courier c : map.getCouriers()) {
            courierThreads.add(new CourierThread(c.id, c.capacity, c.speed));
        }

        this.workingHours = map.getWorkingHours();
    }

    public ArrayList<BakerThread> getBakerThreads() {
        return bakerThreads;
    }

    public ArrayList<CourierThread> getCourierThreads() {
        return courierThreads;
    }

    public Storage getStorage() {
        return storage;
    }

    public int getWorkingHours() {
        return workingHours;
    }
}
