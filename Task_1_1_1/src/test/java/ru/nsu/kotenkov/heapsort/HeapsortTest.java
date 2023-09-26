package ru.nsu.kotenkov.heapsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class HeapsortTest {
    @Test
    void checkMain() {
        Heapsort.main(new String[] {});
        assertTrue(true);
    }

    @Test
    @DisplayName("LargeArray")
    void checkSortingLarge() {
        int[] expected = new int[10000000];

        Random random = new Random();
        for (int i = 0; i < expected.length; i++) {
            expected[i] = random.nextInt();
        }

        int[] actual = Heapsort.sort(expected);
        Arrays.sort(expected);

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("SingleElem")
    void checkSortingSingleElem() {
        int[] expected = {0};
        int[] actual = Heapsort.sort(new int[] {0});

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("SortingEmpty")
    void checkSortingEmpty() {
        int[] expected = {};
        int[] actual = Heapsort.sort(new int[] {});

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("SortingSorted")
    void checkSortedEmpty() {
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] actual = Heapsort.sort(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("SortingReverseSorted")
    void checkSortedReversed() {
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] actual = Heapsort.sort(new int[] {10, 9, 8, 7, 6, 5, 4, 3, 2, 1});

        assertArrayEquals(expected, actual);
    }
}
