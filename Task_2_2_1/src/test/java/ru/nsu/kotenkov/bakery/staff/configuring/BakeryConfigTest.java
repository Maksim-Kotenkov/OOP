package ru.nsu.kotenkov.bakery.staff.configuring;


import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tests for the bakery config.
 */
public class BakeryConfigTest {
    @Test
    @DisplayName("Successful init")
    public void checkConfig() {
        BakeryConfig config = new BakeryConfig();

        assertNotNull(config.getBakerThreads());
        assertNotNull(config.getCourierThreads());
        assertNotNull(config.getStorage());
        assertNotEquals(-1, config.getWorkingHours());
    }
}
