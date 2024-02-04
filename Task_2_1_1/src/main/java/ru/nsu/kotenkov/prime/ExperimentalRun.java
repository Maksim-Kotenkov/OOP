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
     *
     * @param numberOfExperiments number of datasets we'll provide.
     * @param sizes int[] of sizes of datasets, that will be filled with prime numbers
     * @return List of results in format List(Map(Size, Time elapsed))
     * @throws InterruptedException because of parallel computations
     */
    public static List<Map<Integer, Integer>> run(int numberOfExperiments, int[] sizes)
            throws InterruptedException {
        PrimeChecker checker = new PrimeChecker();
        List<Map<Integer, Integer>> resultList = new ArrayList<>(5);  // Always 5 algorithms
        for (int i = 0; i < 5; i++) {
            resultList.add(new HashMap<>());
        }

        List<int[]> testDatasets = new ArrayList<>(numberOfExperiments);
        for (int i = 0; i < numberOfExperiments; i++) {
            testDatasets.add(new int[sizes[i]]);
            Arrays.fill(testDatasets.get(i), 2004991);  // fill with this prime number
        }

        long startTime;
        long endTime;
        long duration;

        for (int i = 0; i < numberOfExperiments; i++) {
            int[] dataset = testDatasets.get(i);

            // LINEAR CHECK
            startTime = System.nanoTime();
            checker.checkListLinear(dataset);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000;
            resultList.get(0).put(dataset.length, (int) duration);

            // TWO THREADS CHECK
            startTime = System.nanoTime();
            checker.checkWithThreads(dataset, 2);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000;
            resultList.get(1).put(dataset.length, (int) duration);

            // THREE THREADS CHECK
            startTime = System.nanoTime();
            checker.checkWithThreads(dataset, 3);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000;
            resultList.get(2).put(dataset.length, (int) duration);

            // FOUR THREADS CHECK
            startTime = System.nanoTime();
            checker.checkWithThreads(dataset, 4);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000;
            resultList.get(3).put(dataset.length, (int) duration);

            // PARALLEL STREAM CHECK
            startTime = System.nanoTime();
            checker.checkWithParallelStream(dataset);
            endTime = System.nanoTime();
            duration = (endTime - startTime) / 1000000;
            resultList.get(4).put(dataset.length, (int) duration);
        }

        return resultList;
    }
}
