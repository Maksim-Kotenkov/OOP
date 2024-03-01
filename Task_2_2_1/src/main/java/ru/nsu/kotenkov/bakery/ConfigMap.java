package ru.nsu.kotenkov.bakery;


import java.util.ArrayList;


public class ConfigMap {
    private ArrayList<Baker> bakers;
    private ArrayList<Courier> couriers;

    public void setBakers(ArrayList<Baker> bakers) {
        this.bakers = bakers;
    }

    public void setCouriers(ArrayList<Courier> couriers) {
        this.couriers = couriers;
    }

    public ArrayList<Baker> getBakers() {
        return bakers;
    }

    public ArrayList<Courier> getCouriers() {
        return couriers;
    }
}
