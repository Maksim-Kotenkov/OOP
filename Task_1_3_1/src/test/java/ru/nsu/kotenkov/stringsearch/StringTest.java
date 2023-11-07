package ru.nsu.kotenkov.stringsearch;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        System.out.println(duration / 1000000000 + "s\n");
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

        System.out.println(duration / 1000000000 + "s\n");
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

        System.out.println(duration / 1000000000 + "s\n");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("StressTest")
    public void checkSmallStressTest() {
        long startTime = System.nanoTime();

        final String target = "PORN";

        BuiltInSearch algo = new BuiltInSearch("EGE_24.txt", target);
        int expected = 19386;
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println(duration / 1000000000 + "s\n");
        assertEquals(expected, actual.size());
    }

    // Проверки на разные кодировки
    // Проверка файла на 15-20гб на НЕ падение out of memory
    @Test
    @DisplayName("Stress test generated")
    public void checkStressTest() throws IOException {
        File file = new File("bigfile.txt");
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);

        Random random = new Random();
        for (int k = 0; k < 15; k++) {
            // form a Gb
            for(int i = 0; i < 1000000000; i++)
            {
                writer.print((char) ('a' + random.nextInt(26)));
            }
        }

        writer.print("something");
        writer.close();
        System.out.println(file.length() / (1024 * 1024) + "Mb");

        long startTime = System.nanoTime();
        final String target = "something";

        BuiltInSearch algo = new BuiltInSearch("bigfile.txt", target);
        List<Integer> actual = algo.find();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1000000000 + "s\n");

        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
        else {
            System.out.println("Failed to delete the file");
        }

        int[] ints = {2115098112};
        List<Integer> expected = new ArrayList<>(ints.length);
        for (int i : ints) {
            expected.add(i);
        }
        assertEquals(expected, actual);
    }
}
