package ru.nsu.kotenkov.bakery.staff.kitchen;


import static org.junit.jupiter.api.Assertions.assertEquals;
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
        testBaker.start();
        try {
            Thread.sleep(12 * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertTrue(testBaker.isAlive());
    }
}
