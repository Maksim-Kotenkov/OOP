package ru.nsu.kotenkov.bakery.staff.configuring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BakeryConfigTest {
    @Test
    @DisplayName("Successful init")
    public void BakeryConfigInit() {
        BakeryConfig config = new BakeryConfig();

        assertNotNull(config.getBakerThreads());
        assertNotNull(config.getCourierThreads());
        assertNotNull(config.getStorage());
        assertNotEquals(-1, config.getWorkingHours());
    }
}
