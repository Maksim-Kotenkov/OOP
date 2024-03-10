package ru.nsu.kotenkov.bakery.staff.configuring;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.kotenkov.bakery.staff.Order;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OrdersConfig {
    public static ArrayList<Order> getOrders() {
        ObjectMapper mapper = new ObjectMapper();

        File json = Paths.get("orders.json").toFile();
        ArrayList<Order> orders = PreviousOrders.get();

        try {
            orders.addAll(mapper.readValue(json,
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class)));
        } catch (IOException e) {
            System.err.println("ORDER CONFIG: Cannot read orders configuration json:" + e.getMessage());
        }

        return orders;
    }
}
