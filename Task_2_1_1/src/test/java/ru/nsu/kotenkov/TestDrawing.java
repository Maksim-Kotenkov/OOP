package ru.nsu.kotenkov;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester for drawing.
 */
public class TestDrawing {
    @Test
    @DisplayName("Drawing")
    void checkDrawing() {
        List<Map<Integer, Integer>> empty = new ArrayList<>();
        DrawingCharts.draw("test", empty);

        assertTrue(true);
    }
}
