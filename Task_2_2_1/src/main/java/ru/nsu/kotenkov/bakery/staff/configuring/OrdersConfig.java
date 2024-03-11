package ru.nsu.kotenkov.bakery.staff.configuring;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * Class for deserializing orders.json.
 */
public class OrdersConfig {
    /**
     * Just one static method that gives us all the orders for today + from prev day.
     *
     * @return orders
     */
    public static ArrayList<Order> getOrders() {
        ObjectMapper mapper = new ObjectMapper();

        File json = Paths.get("orders.json").toFile();
        ArrayList<Order> orders = PreviousOrders.get();

        try {
            orders.addAll(mapper.readValue(json,
                    mapper.getTypeFactory()
                            .constructCollectionType(ArrayList.class,Order.class)));
        } catch (IOException e) {
            System.err.println("ORDER CONFIG: Cannot read orders configuration json:"
                    + e.getMessage());
        }

        return orders;
    }
}
