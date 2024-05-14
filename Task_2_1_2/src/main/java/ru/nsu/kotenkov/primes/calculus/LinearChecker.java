package ru.nsu.kotenkov.primes.calculus;


import static ru.nsu.kotenkov.primes.calculus.PrimeChecker.prime;


/**
 * A linear checker for searching for a non-prime element in an array.
 */
public class LinearChecker {
    /**
     * Linear check of all elements in a list.
     *
     * @param arg target int[]
     * @return is there a non-prime number or not
     */
    public static boolean check(int[] arg) {
        for (int elem : arg) {
            if (!prime(elem)) {
                return true;
            }
        }

        return false;
    }
}
