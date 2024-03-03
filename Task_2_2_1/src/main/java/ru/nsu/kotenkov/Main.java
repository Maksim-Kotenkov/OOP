package ru.nsu.kotenkov;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.kotenkov.bakery.Baker;
import ru.nsu.kotenkov.bakery.Bakery;
import ru.nsu.kotenkov.bakery.Order;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        File json = Paths.get("orders.json").toFile();
        ArrayList<Order> orders = new ArrayList<>();

        try {
            orders = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class));
        } catch (IOException e) {
            System.err.println("Cannot read orders configuration json:" + e.getMessage());
        }

        Bakery bakery = new Bakery(orders);

        bakery.start();
        try {
            bakery.join();
        } catch (InterruptedException e) {
            System.err.println("Bakery thread interrupted");
        }
    }
}
