package ru.nsu.kotenkov.prime;


/**
 * A class for getting results after computations in threads.
 */
public class ThreadWithReturn implements Runnable {
    /**
     * target int[] and the result, that will be saved in the memory
     * and available to be red right after finishing computations.
     */
    int[] target;
    private volatile boolean result;

    /**
     * Constructor.
     *
     * @param listPart our part of a list
     */
    ThreadWithReturn(int[] listPart) {
        this.target = listPart;
    }

    /**
     * Overriding run for saving the result.
     */
    @Override
    public void run() {
        PrimeChecker checker = new PrimeChecker();

        result = checker.checkListLinear(target);
    }

    /**
     * And getter to check the result.
     *
     * @return result of a thread-checking a part of a list
     */
    public boolean isResult() {
        return result;
    }
}
