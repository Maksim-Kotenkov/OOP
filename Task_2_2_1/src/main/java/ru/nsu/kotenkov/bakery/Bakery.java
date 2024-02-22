package ru.nsu.kotenkov.bakery;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Bakery {
    private List<Baker> bakers;
    private List<Courier> couriers;


    public Bakery() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        File json = Paths.get("config.json").toFile();

        // I need to get a big Map with all parameters and then initialize lists
        this.bakers = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Baker[].class)
        ));
    }

    public List<Baker> getBakers() {
        return bakers;
    }

    public List<Courier> getCouriers() {
        return couriers;
    }
}
