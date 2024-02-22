package ru.nsu.kotenkov.prime;


import static ru.nsu.kotenkov.prime.PrimeChecker.prime;


/**
 * A linear checker for searching for a non-prime element in an array.
 */
public class LinearChecker implements Checker {
    /**
     * Linear check of all elements in a list.
     *
     * @param arg target int[]
     * @return is there a non-prime number or not
     */
    @Override
    public boolean check(int[] arg) {
        for (int elem : arg) {
            if (!prime(elem)) {
                return true;
            }
        }

        return false;
    }
}
