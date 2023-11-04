package ru.nsu.kotenkov.stringsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class StringTest {
    @Test
    public void Test1() {
        long startTime = System.nanoTime();

        final String needle = "a";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", needle, 10);
        int[] ints = {3, 19, 31};
        List<Integer> expected = new ArrayList<Integer>(ints.length);
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
    public void TestAs() {
        long startTime = System.nanoTime();

        final String needle = "as";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", needle, 10);
        int[] ints = {19};
        List<Integer> expected = new ArrayList<Integer>(ints.length);
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
    public void TestL() {
        long startTime = System.nanoTime();

        final String needle = "l";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", needle, 10);
        int[] ints = {11, 15, 16, 17, 18, 22, 23};
        List<Integer> expected = new ArrayList<Integer>(ints.length);
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
    public void StressTest() {
        long startTime = System.nanoTime();

        final String needle = "l";

        BuiltInSearch algo = new BuiltInSearch("Test1.txt", needle, 10);
        int[] ints = {11, 15, 16, 17, 18, 22, 23};
        List<Integer> expected = new ArrayList<Integer>(ints.length);
        for (int i : ints) {
            expected.add(i);
        }
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println(duration + "ms\n");
        assertEquals(expected, actual);
    }
}
