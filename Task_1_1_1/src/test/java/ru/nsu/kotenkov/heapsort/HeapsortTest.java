package ru.nsu.kotenkov.heapsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
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
        int [] correct = new int[10000000];

        Random random = new Random();
        for (int i = 0; i < correct.length; i++) {
            correct[i] = random.nextInt();
        }

        int[] arr = Heapsort.sort(correct);
        Arrays.sort(correct);

        assertArrayEquals(arr, correct);
    }

    @Test
    @DisplayName("SingleElem")
    void checkSortingSingleElem() {
        int[] correct = {0};
        int[] arr = Heapsort.sort(new int[] {0});

        assertArrayEquals(arr, correct);
    }

    @Test
    @DisplayName("SortingEmpty")
    void checkSortingEmpty() {
        int[] correct = {};
        int[] arr = Heapsort.sort(new int[] {});

        assertArrayEquals(arr, correct);
    }

    @Test
    @DisplayName("ToHeapTest")
    void checkToHeap() {
        int[] arr = {2, 1, 8};
        int[] correct = {8, 1, 2};

        Heapsort.to_heap(arr, arr.length, 0);
        assertArrayEquals(arr, correct);
    }
}
