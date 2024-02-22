package ru.nsu.kotenkov.prime;


/**
 * Interface for uniting all checkers under one architecture solution with this interface.
 */
public interface Checker {
    /**
     * The method that should solve all your problems with array and non-prime numbers in it.
     *
     * @param arg incoming array
     * @return is there any non-prime number
     */
    public boolean check(int[] arg);
}
