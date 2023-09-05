package Heapsort_package;

public class Heapsort {
    static void sort(int[] arr)
    {
        int n = arr.length;

        for (int i = n/2 - 1; i >= 0; i--) to_heap(arr, n, i);

        for (int i = n - 1; i >= 0; i--)
        {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            to_heap(arr, i, 0);
        }
    }

    static void to_heap(int[] arr, int n, int i)
    {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;

        if (r < n && arr[r] > arr[largest])
            largest = r;

        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            to_heap(arr, n, largest);
        }
    }

//    static void printArray(int[] arr)
//    {
//        int n = arr.length;
//        for (int j : arr) System.out.print(j + " ");
//        System.out.println();
//    }
}
