package ru.nsu.kotenkov.bakery.staff.management;


import org.junit.jupiter.api.*;
import ru.nsu.kotenkov.bakery.staff.Order;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CourierThreadTest {
    OutputStream error;

    @BeforeEach
    public void setUp() {
        error = new ByteArrayOutputStream();
        System.setErr(new PrintStream(error));
    }

    @AfterAll
    public static void tearDown() {
        System.setErr(System.err);
    }

    @Test
    @DisplayName("Baker OK")
    public void checkOkThread() {
        Order testOrder = new Order();
        testOrder.setTimeToDeliver(10);

        CourierThread testCourier = new CourierThread(0, 10, 1);

        testCourier.setOrder(testOrder);
        testCourier.setMyself(new Thread(testCourier));

        testCourier.getMyself().start();
        try {
            Thread.sleep(12 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(testCourier.getMyself().isAlive());
    }

    @Test
    @DisplayName("Courier interruption")
    public synchronized void checkThreadInterruption() {
        Order testOrder = new Order();
        testOrder.setTimeToDeliver(10);

        CourierThread testCourier = new CourierThread(0, 10, 1);

        testCourier.setOrder(testOrder);
        testCourier.setMyself(new Thread(testCourier));

        testCourier.getMyself().start();
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        testCourier.getMyself().interrupt();
        assertTrue(testCourier.getMyself().isInterrupted());
        testCourier.getMyself().interrupt();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals("COURIER: Courier 0 was interrupted while delivering.", error.toString().trim());
    }
}
