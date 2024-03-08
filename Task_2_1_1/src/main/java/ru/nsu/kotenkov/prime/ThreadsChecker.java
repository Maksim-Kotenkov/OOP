package ru.nsu.kotenkov.prime;


import java.util.Arrays;


/**
 * A class for searching for non-prime numbers in arrays with the use of Threads.
 */
public class ThreadsChecker implements Checker {
    private final int numThreads;

    /**
     * Number of threads that is used in this object should be initialized on creation.
     *
     * @param numThreads the number of threads for this obj
     */
    public ThreadsChecker(int numThreads) {
        this.numThreads = numThreads;
    }

    /**
     * List checking with threads.
     *
     * @param arg int[] of numbers
     * @return is there a non-prime number or not
     */
    @Override
    public boolean check(int[] arg) {
        Thread[] threads = new Thread[numThreads];
        ThreadWithReturn[] toReturn = new ThreadWithReturn[numThreads];
        int batchSize = Math.floorDiv(arg.length, numThreads) + 1;

        for (int i = 0; i < numThreads - 1; i++) {
            toReturn[i] = new ThreadWithReturn(arg,
                    i * batchSize,
                    (i + 1) * batchSize,
                    threads);
        }
        toReturn[numThreads - 1] = new ThreadWithReturn(arg,
                (numThreads - 2) * batchSize,
                arg.length,
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
}
