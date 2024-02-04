package ru.nsu.kotenkov;


import java.util.List;
import java.util.Map;
import ru.nsu.kotenkov.prime.ExperimentalRun;


/**
 * Main class.
 */
public class Main {
    /**
     * Based main func.
     *
     * @param args classical things
     * @throws InterruptedException because of parallel computations
     */
    public static void main(String[] args) throws InterruptedException {
        int numberOfExperiments = 8;
        int[] sizes = new int[] {1000, 10000, 50000, 100000, 200000, 500000, 750000, 1000000};
        List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes);

        // Statistics output
        DrawingCharts.draw("Results", resultList);
    }
}