package ru.nsu.kotenkov.bakery.staff.kitchen;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import ru.nsu.kotenkov.bakery.staff.configuring.BakeryConfig;


/**
 * Tests for the kitchen manager.
 */
public class KitchenManagerTest {
    OutputStream newOut;

    @BeforeEach
    public void setUp() {
        newOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOut));
    }

    @AfterAll
    public static void tearDown() {
        System.setOut(System.out);
    }

    @Test
    @DisplayName("Average init")
    public void checkInit() {
        BakeryConfig config = new BakeryConfig();
        ArrayList<Order> orders = new ArrayList<>();

        KitchenManager testKitchen = new KitchenManager(config.getBakerThreads(), orders);

        assertNotNull(testKitchen);
    }

    @Test
    @DisplayName("Average kitchen run without interruptions")
    public void checkRun() {
        BakeryConfig config = new BakeryConfig();
        ArrayList<Order> orders = new ArrayList<>();

        Order customOrder = new Order();
        customOrder.setTimeToCook(10);
        orders.add(customOrder);

        KitchenManager testKitchen = new KitchenManager(config.getBakerThreads(), orders);
        Thread kitchenThread = new Thread(testKitchen);

        assertDoesNotThrow(kitchenThread::start);
    }

    @Test
    @DisplayName("Interrupted kitchen run")
    public void checkRunInterruption() {
        BakeryConfig config = new BakeryConfig();
        ArrayList<Order> orders = new ArrayList<>();

        Order customOrder = new Order();
        customOrder.setTimeToCook(10);
        orders.add(customOrder);

        KitchenManager testKitchen = new KitchenManager(config.getBakerThreads(), orders);
        Thread kitchenThread = new Thread(testKitchen);

        kitchenThread.start();

        try {
            Thread.sleep(8 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        kitchenThread.interrupt();
        try {
            Thread.sleep(4000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals("KITCHEN: Orders number:1\n"
                + "BAKER: Baker 0 started cooking order 0\n"
                + "\n"
                + "OFFICE: STOPPING THE WORK\n"
                + "\n"
                + "OFFICE: Kitchen finished the day\n"
                + "DELIVERY: Waiting for all the couriers to finish the work\n"
                + "OFFICE: Delivery finished the day\n"
                + "OFFICE: Office finished the day\n"
                + "BAKER: Baker 0 finished cooking order 0\n"
                + "STORAGE: add order 0\n"
                + "BAKER: Baker 0 stored order 0\n", newOut.toString());
    }
}
