package ru.nsu.kotenkov.bakery;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.configuring.BakeryConfig;
import ru.nsu.kotenkov.bakery.staff.management.DeliveryManager;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Thread bakeryThread = new Thread(testBakery);

        assertDoesNotThrow(bakeryThread::start);
    }

    @Test
    @DisplayName("Bakery run with interruption")
    public void checkRunInterruption() {
        ArrayList<Order> orders = new ArrayList<>();

        Order customOrder = new Order();
        customOrder.setTimeToCook(10);
        customOrder.setTimeToDeliver(10);
        orders.add(customOrder);

        Bakery testBakery = new Bakery(orders);
        testBakery.setWorkingHours(20);
        Thread bakeryThread = new Thread(testBakery);

        bakeryThread.start();

        bakeryThread.interrupt();
        assertTrue(bakeryThread.isInterrupted());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals("OFFICE: Kitchen thread was interrupted during the work", error.toString().trim());
    }
}
