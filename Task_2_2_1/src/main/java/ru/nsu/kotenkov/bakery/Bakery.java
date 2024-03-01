package ru.nsu.kotenkov.bakery;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class Bakery {
    private ArrayList<Baker> bakers;
    private ArrayList<Courier> couriers;


    public Bakery() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File json = Paths.get("config.json").toFile();

        ConfigMap map = mapper.readValue(json, ConfigMap.class);
        this.bakers = map.getBakers();
        this.couriers = map.getCouriers();
        for (Baker b : this.bakers) {
            System.out.println(b.getEfficiency());
        }

    }

    public ArrayList<Baker> getBakers() {
        return bakers;
    }

    public ArrayList<Courier> getCouriers() {
        return couriers;
    }
}
