package ru.nsu.kotenkov.prime;


import java.util.Arrays;
import java.util.List;


/**
 * A class for searching for non-prime elements in array with the use of ParallelStream.
 */
public class ParallelStreamChecker implements Checker {
    /**
     * Checking the list with the use of ParallelStream().
     *
     * @param arg target int[]
     * @return is there a non-prime number or not
     */
    @Override
    public boolean check(int[] arg) {
        List<Integer> targetList = Arrays.stream(arg).boxed().toList();

        return targetList.parallelStream().map(PrimeChecker::prime).anyMatch(Boolean.FALSE::equals);
    }
}
