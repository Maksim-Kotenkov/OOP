package ru.nsu.kotenkov.stringsearch;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester class.
 */
public class StringTest {
    @Test
    public void checkTest1() {
        long startTime = System.nanoTime();

        final String target = "a";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", target);
        int[] ints = {3, 19, 31};
        List<Integer> expected = new ArrayList<>(ints.length);
        for (int i : ints) {
            expected.add(i);
        }
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println(duration + "ms\n");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Pair of symbols at the edge of batches")
    public void checkTestAs() {
        long startTime = System.nanoTime();

        final String target = "as";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", target);
        int[] ints = {19};
        List<Integer> expected = new ArrayList<>(ints.length);
        for (int i : ints) {
            expected.add(i);
        }
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println(duration + "ms\n");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Many l")
    public void checkTestL() {
        long startTime = System.nanoTime();

        final String target = "l";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", target);
        int[] ints = {11, 15, 16, 17, 18, 22, 23};
        List<Integer> expected = new ArrayList<>(ints.length);
        for (int i : ints) {
            expected.add(i);
        }
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println(duration + "ms\n");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("StressTest")
    public void checkStressTest() {
        long startTime = System.nanoTime();

        final String target = "PORN";

        BuiltInSearch algo = new BuiltInSearch("EGE_24.txt", target);
        int expected = 19386;
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println(duration + "ms\n");
        assertEquals(expected, actual.size());
    }
}
