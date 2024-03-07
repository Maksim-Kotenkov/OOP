package ru.nsu.kotenkov.bakery.staff.configuring;


import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.kitchen.Baker;
import ru.nsu.kotenkov.bakery.staff.management.Courier;


/**
 * We deserialize json to this structure.
 */
public class ConfigMap {
    private ArrayList<Baker> bakers;
    private ArrayList<Courier> couriers;
    private int storage;
    private int workingHours;

    /**
     * Setters are used by ObjectMapper from Jackson.
     *
     * @param bakers for baker init
     */
    public void setBakers(ArrayList<Baker> bakers) {
        this.bakers = bakers;
    }

    /**
     * Setters are used by ObjectMapper from Jackson.
     *
     * @param couriers for couriers init
     */
    public void setCouriers(ArrayList<Courier> couriers) {
        this.couriers = couriers;
    }

    /**
     * Setters are used by ObjectMapper from Jackson.
     *
     * @param storage for storage init
     */
    public void setStorage(int storage) {
        this.storage = storage;
    }

    /**
     * Setters are used by ObjectMapper from Jackson.
     *
     * @param workingHours for working hours init
     */
    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    /**
     * Getter.
     *
     * @return list of bakers
     */
    public ArrayList<Baker> getBakers() {
        return bakers;
    }

    /**
     * Getter.
     *
     * @return list of couriers
     */
    public ArrayList<Courier> getCouriers() {
        return couriers;
    }

    /**
     * Getter.
     *
     * @return a storage
     */
    public int getStorage() {
        return storage;
    }

    /**
     * Getter.
     *
     * @return int for working hours
     */
    public int getWorkingHours() {
        return workingHours;
    }
}
