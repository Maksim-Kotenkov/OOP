package ru.nsu.kotenkov.prime;


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
     */
    public boolean checkWithThreads(int[] targets, int numThreads) {
        Thread[] threads = new Thread[numThreads];
        ThreadWithReturn[] toReturn = new ThreadWithReturn[numThreads];
        int batchSize = Math.floorDiv(targets.length, numThreads) + 1;

        for (int i = 0; i < numThreads - 1; i++) {
            toReturn[i] = new ThreadWithReturn(targets,
                    i * batchSize,
                    (i + 1) * batchSize,
                    threads);
        }
        toReturn[numThreads - 1] = new ThreadWithReturn(targets,
                (numThreads - 2) * batchSize,
                targets.length,
                threads);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(toReturn[i]);
        }

        for (int i = 0; i < threads.length; i++) {
            toReturn[i].setMyself(threads[i]);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.err.println("Got interrupted");
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

        return targetList.parallelStream().map(this::prime).anyMatch(Boolean.FALSE::equals);
    }
}
