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
    private final int[] target;
    private final int begin;
    private final int end;
    private final Thread[] otherThreads;
    private Thread myself;
    private volatile boolean result;

    /**
     * Constructor.
     *
     * @param listPart our part of a list
     */
    ThreadWithReturn(int[] listPart, int begin, int end, Thread[] otherThreads) {
        this.target = listPart;
        this.begin = begin;
        this.end = end;
        this.otherThreads = otherThreads;
    }

    public void setMyself(Thread myself) {
        this.myself = myself;
    }

    /**
     * Overriding run for saving the result.
     */
    @Override
    public void run() {
        for (int elem = this.begin; (elem < this.end) && (elem < this.target.length); elem++) {
            if (!PrimeChecker.prime(this.target[elem])) {
                this.result = true;
                for (Thread nbThread : this.otherThreads) {
                    nbThread.interrupt();
                }
                return;
            }
            if (this.myself.isInterrupted()) {
                break;
            }
        }

        this.result = false;
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
