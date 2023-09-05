package heapsort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class HeapsortTest {

    @Test
    @DisplayName("LargeArray")
    void checkSortingLarge() {
        int[] correct = {5, 6, 7, 11, 12, 13};
        int[] arr = Heapsort.sort(new int[] {12, 11, 13, 5, 6, 7});

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
        int[] arr = {8, 1, 2};
        int[] correct = {8, 1, 2};

        Heapsort.to_heap(arr, arr.length, 0);
        assertArrayEquals(arr, correct);
    }
}
