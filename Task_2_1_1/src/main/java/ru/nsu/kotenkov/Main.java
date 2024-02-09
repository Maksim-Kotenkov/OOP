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
        int numberOfExperiments = 4;
        int[] sizes = new int[] {2000000, 5000000, 10000000, 20000000};
        List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes,
                1, true);

        // Statistics output
        DrawingCharts.draw("Results", resultList);
    }
}
