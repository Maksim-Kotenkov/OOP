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

        // I need to get a big Map with all parameters and then initialize lists
        HashMap<String, ArrayList<HashMap<String, Integer>>> staff = mapper.readValue(json,
                        mapper.getTypeFactory().constructMapLikeType(HashMap.class, String.class, ArrayList.class));

        System.out.println("Couriers: " + staff.get("couriers") + " Bakers: " + staff.get("bakers"));
        System.out.println(staff.get("couriers").getClass() + " " + staff.get("bakers").getClass());
        System.out.println(staff.get("couriers").get(0).getClass() + " " + staff.get("bakers").get(0).getClass());

        try {
            this.bakers = mapper.readValue(staff.get("bakers").toString().replaceAll("=", ":"),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Baker.class));
            this.couriers = mapper.readValue(staff.get("couriers").toString().replaceAll("=", ":"),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Courier.class));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


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
