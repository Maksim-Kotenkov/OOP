package ru.nsu.kotenkov.primes.calculus;


/**
 * Class for being a prime number.
 */
public class PrimeChecker {
    /**
     * Function for direct number check.
     *
     * @param number argument
     * @return is prime or not
     */
    public static boolean prime(int number) {
        for (int i = 2; i <= Math.ceil(Math.sqrt(number)); i++) {
            if (Math.floorMod(number, i) == 0) {
                return false;
            }
        }

        return true;
    }
}