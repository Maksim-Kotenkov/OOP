package ru.nsu.kotenkov.bakery.staff.kitchen;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.management.Storage;


/**
 * Tests for the baker thread.
 */
public class BakerThreadTest {
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
        Storage storage = new Storage(2);

        BakerThread testBaker = new BakerThread(0, 1, storage);

        testBaker.setOrder(testOrder);
        testBaker.setMyself(new Thread(testBaker));

        testBaker.getMyself().start();
        try {
            Thread.sleep(12 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertFalse(testBaker.getMyself().isAlive());
    }

    @Test
    @DisplayName("Baker interruption")
    public void checkThreadInterruption() {
        Order testOrder = new Order();
        testOrder.setTimeToCook(10);
        Storage storage = new Storage(2);

        BakerThread testBaker = new BakerThread(0, 1, storage);

        testBaker.setOrder(testOrder);
        testBaker.setMyself(new Thread(testBaker));

        testBaker.getMyself().start();
        try {
            Thread.sleep(5 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        testBaker.getMyself().interrupt();
        assertTrue(testBaker.getMyself().isInterrupted());
        testBaker.getMyself().interrupt();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals("BAKER: Baker 0 was interrupted while cooking.", error.toString().trim());
    }
}
