package Heapsort_package;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void checkSorting() {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int[] correct = {5, 6, 7, 11, 12, 13};
        Heapsort.sort(arr);

        assertArrayEquals(arr, correct);
    }
}
