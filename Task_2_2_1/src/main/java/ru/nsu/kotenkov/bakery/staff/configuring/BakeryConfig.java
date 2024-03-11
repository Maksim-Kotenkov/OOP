package ru.nsu.kotenkov.bakery.staff.configuring;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.kitchen.Baker;
import ru.nsu.kotenkov.bakery.staff.kitchen.BakerThread;
import ru.nsu.kotenkov.bakery.staff.management.Courier;
import ru.nsu.kotenkov.bakery.staff.management.CourierThread;
import ru.nsu.kotenkov.bakery.staff.management.Storage;


/**
 * Let's use SOLID METHODS, I mean, at least single-responsibility.
 * We need to separate deserializing json from the Bakery initializing. This is it.
 */
public class BakeryConfig {
    private final ArrayList<BakerThread> bakerThreads;
    private final ArrayList<CourierThread> courierThreads;
    private final Storage storage;
    private final int workingHours;

    /**
     * Json is deserialized into ConfigMap structure, but we need to create other structs.
     * A list of bakerThreads and courierThreads that will be run later.
     * A storage.
     * WorkingHours too.
     */
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

    /**
     * Getter.
     *
     * @return a bakerThread list to start them
     */
    public ArrayList<BakerThread> getBakerThreads() {
        return bakerThreads;
    }

    /**
     * Getter.
     *
     * @return a courierThread list to start them
     */
    public ArrayList<CourierThread> getCourierThreads() {
        return courierThreads;
    }

    /**
     * Getter.
     *
     * @return the storage of our Bakery
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Getter.
     *
     * @return how many hours (seconds irl) Bakery should work
     */
    public int getWorkingHours() {
        return workingHours;
    }
}
