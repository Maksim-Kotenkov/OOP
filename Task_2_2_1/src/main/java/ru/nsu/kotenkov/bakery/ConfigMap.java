package ru.nsu.kotenkov.bakery;


import ru.nsu.kotenkov.bakery.kitchen.Baker;
import ru.nsu.kotenkov.bakery.management.Courier;

import java.util.ArrayList;


public class ConfigMap {
    private ArrayList<Baker> bakers;
    private ArrayList<Courier> couriers;
    private int storage;

    public void setBakers(ArrayList<Baker> bakers) {
        this.bakers = bakers;
    }

    public void setCouriers(ArrayList<Courier> couriers) {
        this.couriers = couriers;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public ArrayList<Baker> getBakers() {
        return bakers;
    }

    public ArrayList<Courier> getCouriers() {
        return couriers;
    }

    public int getStorage() {
        return storage;
    }
}
