package ru.nsu.kotenkov.bakery.staff.configuring;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * A class that deserialize prevDayOrders.json.
 */
public class PreviousOrders {
    /**
     * Method to store orders in prevDayOrders.json.
     *
     * @param orders prev day orders
     */
    public static void store(List<Order> orders) {
        ObjectMapper mapper = new ObjectMapper();
        File json = Paths.get("prevDayOrders.json").toFile();
        System.out.println("PREV DAY CONFIG: " + orders.size() + " orders to the next day");

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(json, orders);
        } catch (IOException e) {
            System.err.println("PREV DAY CONFIG: Cannot write prevDayOrders: "
                    + e.getMessage());
        }
    }

    /**
     * Method to get prev day orders from json.
     *
     * @return prev day orders
     */
    public static ArrayList<Order> get() {
        ObjectMapper mapper = new ObjectMapper();
        File json = Paths.get("prevDayOrders.json").toFile();
        ArrayList<Order> orders = new ArrayList<>();

        try {
            orders.addAll(mapper.readValue(json,
                    mapper.getTypeFactory()
                            .constructCollectionType(ArrayList.class, Order.class)));
        } catch (IOException e) {
            System.err.println("PREV DAY CONFIG: " + "Cannot read prevDayOrders: "
                    + e.getMessage());
        }

        System.out.println("PREV DAY CONFIG: " + orders.size() + " orders from the previous day");
        return orders;
    }
}
