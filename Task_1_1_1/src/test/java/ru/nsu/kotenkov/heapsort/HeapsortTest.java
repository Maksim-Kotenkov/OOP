package ru.nsu.kotenkov.heapsort;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;


class HeapsortTest {
    @Test
    void checkMain() {
        Heapsort.main(new String[] {});
        assertTrue(true);
    }

    @Test
    @DisplayName("LargeArray")
    void checkSortingLarge() {
        int [] rArr = new int[10000000];

        Random rClass = new Random();
        for(int i=0;i<rArr.length;i++)
        {
            rArr[i] = rClass.nextInt();
        }

        int[] arr = Heapsort.sort(rArr);
        Arrays.sort(rArr);

        assertArrayEquals(arr, rArr);
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
