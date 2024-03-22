package ru.nsu.kotenkov.bakery;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.bakery.staff.Order;


/**
 * Tests for the bakery.
 */
public class BakeryTest {
    OutputStream error = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setErr(new PrintStream(error));
    }

    @AfterAll
    public static void tearDown() {
        System.setErr(System.err);
    }

    @Test
    @DisplayName("Average init")
    public void checkInit() {
        ArrayList<Order> orders = new ArrayList<>();

        Order customOrder = new Order();
        customOrder.setTimeToCook(10);
        orders.add(customOrder);

        Bakery testBakery = new Bakery(orders);

        assertNotNull(testBakery);
    }

    @Test
    @DisplayName("Bakery run without interruptions")
    public void checkRun() {
        ArrayList<Order> orders = new ArrayList<>();

        Order customOrder = new Order();
        customOrder.setTimeToCook(10);
        orders.add(customOrder);

        Bakery testBakery = new Bakery(orders);

        assertDoesNotThrow(testBakery::run);
    }

}
