package ru.nsu.kotenkov.bakery.staff.configuring;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * Tests for the previous orders config.
 */
public class PreviousOrderTest {
    @Test
    @DisplayName("PrevOrders: Some orders on init")
    public void checkInit() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File json = Paths.get("prevDayOrders.json").toFile();
        final ArrayList<Order> backup = mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class));

        final ArrayList<Order> testOrders = new ArrayList<>();
        Order ord = new Order();
        ord.setTimeToDeliver(8);
        ord.setId(1);
        ord.setTimeToCook(10);

        testOrders.add(ord);
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, testOrders);
        ArrayList<Order> actual = PreviousOrders.get();

        assertEquals(1, actual.size());
        assertEquals(1, actual.get(0).getId());
        assertEquals(8, actual.get(0).getTimeToDeliver());
        assertEquals(10, actual.get(0).getTimeToCook());

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("PrevOrders: Empty init")
    public void checkEmptyInit() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File json = Paths.get("prevDayOrders.json").toFile();
        final ArrayList<Order> backup = mapper.readValue(json,
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, Order.class));

        final ArrayList<Order> testOrders = new ArrayList<>();

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, testOrders);
        ArrayList<Order> actual = PreviousOrders.get();

        assertEquals(0, actual.size());

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }
}
