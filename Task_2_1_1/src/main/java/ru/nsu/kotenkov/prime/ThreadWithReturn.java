package ru.nsu.kotenkov.prime;


/**
 * A class for getting results after computations in threads.
 */
public class ThreadWithReturn implements Runnable {
    /**
     * target int[] and the result, that will be saved in the memory
     * and available to be red right after finishing computations.
     * begin and end for taking only a part.
     */
    int[] target;
    int begin;
    int end;
    private volatile boolean result;

    /**
     * Constructor.
     *
     * @param listPart our part of a list
     */
    ThreadWithReturn(int[] listPart, int begin, int end) {
        this.target = listPart;
        this.begin = begin;
        this.end = end;
    }

    /**
     * Overriding run for saving the result.
     */
    @Override
    public void run() {
        PrimeChecker checker = new PrimeChecker();

//        result = checker.checkListLinear(target);

        for (int elem = this.begin; (elem < this.end) && (elem < this.target.length); elem++) {
            if (!checker.prime(this.target[elem])) {
                result = true;
                return;
            }
        }

        result = false;
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
