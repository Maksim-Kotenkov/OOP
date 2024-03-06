package ru.nsu.kotenkov.bakery.staff.management;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.configuring.BakeryConfig;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryManagerTest {
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

        DeliveryManager testKitchen = new DeliveryManager(config.getCourierThreads(), config.getStorage());

        assertNotNull(testKitchen);
    }

    @Test
    @DisplayName("Average delivery run without interruptions")
    public void checkRun() {
        BakeryConfig config = new BakeryConfig();
        ArrayList<Order> orders = new ArrayList<>();

        Order customOrder = new Order();
        customOrder.setTimeToDeliver(10);
        orders.add(customOrder);

        Storage testStorage = config.getStorage();
        testStorage.addOrder(customOrder);

        DeliveryManager testDelivery = new DeliveryManager(config.getCourierThreads(), testStorage);
        Thread deliveryThread = new Thread(testDelivery);

        assertDoesNotThrow(deliveryThread::start);
    }

    @Test
    @DisplayName("Interrupted delivery run")
    public void checkRunInterruption() {
        BakeryConfig config = new BakeryConfig();

        Order customOrder = new Order();
        customOrder.setTimeToDeliver(30);

        Storage testStorage = config.getStorage();
        testStorage.addOrder(customOrder);

        DeliveryManager testDelivery = new DeliveryManager(config.getCourierThreads(), testStorage);
        Thread deliveryThread = new Thread(testDelivery);

        deliveryThread.start();

        try {
            Thread.sleep(8 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        deliveryThread.interrupt();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals("STORAGE: add order 0\n"
                + "STORAGE: remove order 0\n"
                + "COURIER: Courier 0 took the order 0 with the time to deliver: 30\n"
                + "DELIVERY: Waiting for all the couriers to finish the work\n", newOut.toString());
    }
}
