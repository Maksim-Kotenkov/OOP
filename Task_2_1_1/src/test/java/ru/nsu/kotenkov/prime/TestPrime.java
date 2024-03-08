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
        assertFalse(PrimeChecker.prime(4));
        assertTrue(PrimeChecker.prime(5));
        assertTrue(PrimeChecker.prime(7));
    }

    @Test
    @DisplayName("Just prime 12")
    void checkFalseBasicPrimeCall() {
        assertFalse(PrimeChecker.prime(12));
    }

    @Test
    @DisplayName("Border values")
    void checkBorder() {
        assertTrue(PrimeChecker.prime(0));
        assertTrue(PrimeChecker.prime(1));
        assertFalse(PrimeChecker.prime(2));
        assertTrue(PrimeChecker.prime(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("List from the task")
    void checkList() {
        LinearChecker checker = new LinearChecker();
        int[] testData = new int[] {6, 8, 7, 13, 5, 9, 4};

        assertTrue(checker.check(testData));
    }

    @Test
    @DisplayName("List from the task")
    void checkList2() {
        LinearChecker checker = new LinearChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertFalse(checker.check(testData));
    }

    @Test
    @DisplayName("List from the task")
    void checkListThreads() {
        ThreadsChecker checker = new ThreadsChecker(2);
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertFalse(checker.check(testData));
    }

    @Test
    @DisplayName("Parallel stream false from the task")
    void checkListParallelStream() {
        ParallelStreamChecker checker = new ParallelStreamChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        assertFalse(checker.check(testData));
    }

    @Test
    @DisplayName("Parallel stream but true")
    void checkListParallelStreamTrue() {
        ParallelStreamChecker checker = new ParallelStreamChecker();
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053, 6};

        assertTrue(checker.check(testData));
    }

    @Test
    void checkCustom() {
        int numberOfExperiments = 2;
        int[] sizes = new int[] {1000, 10000};
        List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes,
                1, false);

        assertEquals(Arrays.stream(sizes).boxed().collect(Collectors.toCollection(HashSet::new)),
                resultList.get(0).keySet());
    }

    @Test
    @DisplayName("Executing experimental run class func")
    void checkCustom2() {
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
                                    6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        ThreadWithReturn[] custom = new ThreadWithReturn[1];
        Thread[] threads = new Thread[1];
        custom[0] = new ThreadWithReturn(testData, 0, testData.length, threads);

        threads[0] = new Thread(custom[0]);
        custom[0].setMyself(threads[0]);
        threads[0].start();
        threads[0].join();

        assertFalse(custom[0].isResult());
    }

    @Test
    @DisplayName("8 elems 6 threads")
    void checkStrangeThreads() {
        ThreadsChecker checker = new ThreadsChecker(8);
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967};

        assertFalse(checker.check(testData));
    }

    @Test
    @DisplayName("8 elems 100 threads")
    void checkStrangeThreads2() {
        ThreadsChecker checker = new ThreadsChecker(100);
        int[] testData = new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967};

        assertFalse(checker.check(testData));
    }
}
