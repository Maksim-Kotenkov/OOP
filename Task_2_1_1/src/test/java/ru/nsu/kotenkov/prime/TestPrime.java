package ru.nsu.kotenkov.prime;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester for prime package.
 */
public class TestPrime {
    @Test
    @DisplayName("Just 4, 5, 7")
    void checkBasicPrimeCall() {
        PrimeChecker checker = new PrimeChecker();

        assertFalse(checker.prime(4));
        assertTrue(checker.prime(5));
        assertTrue(checker.prime(7));
    }

    @Test
    @DisplayName("Just prime 12")
    void checkFalseBasicPrimeCall() {
        PrimeChecker checker = new PrimeChecker();

        assertFalse(checker.prime(12));
    }

    @Test
    @DisplayName("Border values")
    void checkBorder() {
        PrimeChecker checker = new PrimeChecker();

        assertTrue(checker.prime(0));
        assertTrue(checker.prime(1));
        assertFalse(checker.prime(2));
        assertTrue(checker.prime(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("List from the task")
    void checkList() {
        PrimeChecker checker = new PrimeChecker();
        int[] testData = new int[] {6, 8, 7, 13, 5, 9, 4};

        assertTrue(checker.checkListLinear(testData));
    }

    @Test
    @DisplayName("List from the task")
    void checkList2() {
        PrimeChecker checker = new PrimeChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertFalse(checker.checkListLinear(testData));
    }

    @Test
    @DisplayName("List from the task")
    void checkListThreads() throws InterruptedException {
        PrimeChecker checker = new PrimeChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertFalse(checker.checkWithThreads(testData, 2));
    }

    @Test
    @DisplayName("Parallel stream false from the task")
    void checkListParallelStream() {
        PrimeChecker checker = new PrimeChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertFalse(checker.checkWithParallelStream(testData));
    }

    @Test
    @DisplayName("Parallel stream but true")
    void checkListParallelStreamTrue() {
        PrimeChecker checker = new PrimeChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053, 6};

        assertTrue(checker.checkWithParallelStream(testData));
    }

    @Test
    void checkCustom() throws InterruptedException {
        int numberOfExperiments = 2;
        int[] sizes = new int[] {1000, 10000};
        List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes,
                1, false);

        assertEquals(Arrays.stream(sizes).boxed().collect(Collectors.toCollection(HashSet::new)),
                resultList.get(0).keySet());
    }

    @Test
    @DisplayName("Executing experimental run class func")
    void checkCustom2() throws InterruptedException {
        int numberOfExperiments = 0;
        int[] sizes = new int[0];
        List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes,
                1, false);

        assertTrue(resultList.get(0).isEmpty());
    }

    @Test
    @DisplayName("Custom thread with possibility to return")
    void checkCustomThread() throws InterruptedException {
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053, 6};

        ThreadWithReturn[] custom = new ThreadWithReturn[1];
        Thread[] threads = new Thread[1];
        custom[0] = new ThreadWithReturn(testData, 0, testData.length);

        threads[0] = new Thread(custom[0]);
        threads[0].start();
        threads[0].join();

        assertTrue(custom[0].isResult());
    }

    @Test
    @DisplayName("8 elems 6 threads")
    void checkStrangeThreads() throws InterruptedException {
        PrimeChecker checker = new PrimeChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967};

        assertFalse(checker.checkWithThreads(testData, 8));
    }
}
