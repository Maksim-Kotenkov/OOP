package ru.nsu.kotenkov.prime;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class for checking is there a non-prime number in a List.
 */
public class PrimeChecker {
    /**
     * Function for direct number check.
     *
     * @param number argument
     * @return is prime or not
     */
    public boolean prime(int number) {
        for (int i = 2; i <= Math.ceil(Math.sqrt(number)); i++) {
            if (Math.floorMod(number, i) == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Linear check of all elements in a list.
     *
     * @param target target int[]
     * @return is there a non-prime number or not
     */
    public boolean checkListLinear(int[] target) {
        for (int elem : target) {
            if (!prime(elem)) {
                return true;
            }
        }

        return false;
    }

    /**
     * List checking with threads.
     *
     * @param targets int[] of numbers
     * @param numThreads the number of threads we want to use
     * @return is there a non-prime number or not
     * @throws InterruptedException because of parallel computations
     */
    public boolean checkWithThreads(int[] targets, int numThreads) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        ThreadWithReturn[] toReturn = new ThreadWithReturn[numThreads];
        int batchSize = Math.ceilDiv(targets.length, numThreads);

        for (int i = 0; i < numThreads; i++) {
            toReturn[i] = new ThreadWithReturn(Arrays.copyOfRange(targets,
                    i * batchSize,
                    (i + 1) * batchSize));
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(toReturn[i]);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            threads[i].join();
        }

        return Arrays.stream(toReturn).anyMatch(ThreadWithReturn::isResult);
    }

    /**
     * Checking the list with the use of ParallelStream().
     *
     * @param targets target int[]
     * @return is there a non-prime number or not
     */
    public boolean checkWithParallelStream(int[] targets) {
        List<Integer> targetList = Arrays.stream(targets).boxed().toList();
        List<Boolean> result = new ArrayList<>(targets.length);

        targetList.parallelStream().forEach(elem -> result.add(prime(elem)));

        return result.stream().anyMatch(Boolean.FALSE::equals);
    }
}
