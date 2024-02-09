package ru.nsu.kotenkov.prime;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A class to specify parameters of our experiment.
 * You can choose the number of executions and set up sizes of datasets,
 * that will be used in these experiments.
 */
public class ExperimentalRun {
    /**
     * The function to be called with parameters of our experiment.
     * Linear algorithm is very slow and has big numbers on the graphical interpretation, so
     * it's better to skip it and compare other algorithms with close-up graphics.
     *
     * @param numberOfExperiments number of datasets we'll provide.
     * @param sizes int[] of sizes of datasets, that will be filled with prime numbers
     * @param numberOfRuns the number of executions for every algo to get the mean result
     * @param skipLinear do you want to skip calculations for linear algo?
     * @return List of results in format List(Map(Size, Time elapsed))
     * @throws InterruptedException because of parallel computations
     */
    public static List<Map<Integer, Integer>> run(int numberOfExperiments, int[] sizes,
                                                  int numberOfRuns, boolean skipLinear)
            throws InterruptedException {
        PrimeChecker checker = new PrimeChecker();
        List<Map<Integer, Integer>> resultList = new ArrayList<>(7);  // Always 7 algorithms
        for (int i = 0; i < 7; i++) {
            resultList.add(new HashMap<>());
        }

        List<int[]> testDatasets = new ArrayList<>(numberOfExperiments);
        for (int i = 0; i < numberOfExperiments; i++) {
            testDatasets.add(new int[sizes[i]]);
            Arrays.fill(testDatasets.get(i), 2004991);  // fill with this prime number
        }

        long startTime;
        long endTime;
        long[] duration = new long[7];

        for (int i = 0; i < numberOfExperiments; i++) {
            int[] dataset = testDatasets.get(i);

            // LINEAR CHECK
            if (!skipLinear) {
                for (int j = 0; j < numberOfRuns; j++) {
                    startTime = System.nanoTime();
                    checker.checkListLinear(dataset);
                    endTime = System.nanoTime();
                    duration[j] = (endTime - startTime) / 1000000;
                }
                resultList.get(0).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);
            } else {
                resultList.get(0).put(dataset.length, 0);
            }

            // FOUR THREADS CHECK
            for (int j = 0; j < numberOfRuns; j++) {
                startTime = System.nanoTime();
                checker.checkWithThreads(dataset, 4);
                endTime = System.nanoTime();
                duration[j] = (endTime - startTime) / 1000000;
            }
            resultList.get(1).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);

            // EIGHT THREADS CHECK
            for (int j = 0; j < numberOfRuns; j++) {
                startTime = System.nanoTime();
                checker.checkWithThreads(dataset, 8);
                endTime = System.nanoTime();
                duration[j] = (endTime - startTime) / 1000000;
            }
            resultList.get(2).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);

            // FIFTY THREADS CHECK
            for (int j = 0; j < numberOfRuns; j++) {
                startTime = System.nanoTime();
                checker.checkWithThreads(dataset, 50);
                endTime = System.nanoTime();
                duration[j] = (endTime - startTime) / 1000000;
            }
            resultList.get(3).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);

            // A HUNDRED THREADS CHECK
            for (int j = 0; j < numberOfRuns; j++) {
                startTime = System.nanoTime();
                checker.checkWithThreads(dataset, 100);
                endTime = System.nanoTime();
                duration[j] = (endTime - startTime) / 1000000;
            }
            resultList.get(4).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);

            // FIVE HUNDRED THREADS CHECK
            for (int j = 0; j < numberOfRuns; j++) {
                startTime = System.nanoTime();
                checker.checkWithThreads(dataset, 500);
                endTime = System.nanoTime();
                duration[j] = (endTime - startTime) / 1000000;
            }
            resultList.get(5).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);

            // PARALLEL STREAM CHECK
            for (int j = 0; j < numberOfRuns; j++) {
                startTime = System.nanoTime();
                checker.checkWithParallelStream(dataset);
                endTime = System.nanoTime();
                duration[j] = (endTime - startTime) / 1000000;
            }
            resultList.get(6).put(dataset.length, (int) Arrays.stream(duration).asDoubleStream().sum() / 5);
        }

        return resultList;
    }
}
