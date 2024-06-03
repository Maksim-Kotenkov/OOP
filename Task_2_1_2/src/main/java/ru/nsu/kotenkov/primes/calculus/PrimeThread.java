package ru.nsu.kotenkov.primes.calculus;


/**
 * A Thread class with possibility to get the results.
 */
public class PrimeThread implements Runnable {
    /**
     * target int[] and the result, that will be saved in the memory
     * and available to be red right after finishing computations.
     * begin and end for taking only a part.
     */
    private final int[] target;
    private volatile boolean result;
    private boolean finished = false;

    /**
     * Constructor.
     *
     * @param listPart our part of a list
     */
    public PrimeThread(int[] listPart) {
        this.target = listPart;
    }

    /**
     * Overriding run for saving the result.
     */
    @Override
    public void run() {
        synchronized (this) {
            for (int elem : target) {
                if (!PrimeChecker.prime(elem)) {
                    this.result = true;
                    this.finished = true;
                    notifyAll();
                    return;
                }
            }
            this.result = false;
            this.finished = true;
            notifyAll();
        }
    }

    /**
     * And getter to check the result.
     *
     * @return result of a thread-checking a part of a list
     */
    public boolean isResult() {
        return result;
    }

    /**
     * And getter to check the state.
     *
     * @return state
     */
    public boolean isFinished() {
        return finished;
    }
}
