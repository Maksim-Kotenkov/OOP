package heapsort;

/**
 * Класс пирамидальной сортировки с процедурами sort (основная) и to_heap (вспомогательная).
 *
 * @author Котенков Максим
 *
 * @version 1.0
 */
public class Heapsort {
    /**
     * Функция получения отсортированного по неубыванию массива из произвольного.
     *
     * @param arr - массив на вход
     * @return возвращает отсортированный массив int
     */
    static int[] sort(int[] arr)
    {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            to_heap(arr, n, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            to_heap(arr, i, 0);
        }

        return arr;
    }

    /**
     * Процедура премещения бОльших элементов в корни своих поддеревьев.
     *
     * @param arr - массив на вход
     * @param n - уменьшенный размер кучи (так как мы тяжелые элементы перекидываем в конец)
     * @param i - наибольшая вершина, детей которой мы будем сравнивать с ней
     */
    static void to_heap(int[] arr, int n, int i)
    {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }

        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            to_heap(arr, n, largest);
        }
    }
}
