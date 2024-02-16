package ru.nsu.kotenkov;


import java.io.IOException;
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
        int numberOfExperiments = 3;
        int[] sizes = new int[] {15000000, 18000000, 20000000};
        try {
            List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes,
                    1, true);
            // Statistics output
            DrawingCharts.draw("Results", resultList);
        } catch (InterruptedException exception) {
            System.err.println("Calculations were interrupted: " + exception.getMessage());
        } catch (IOException exception) {
            System.err.println("IO operation was interrupted: " + exception.getMessage());
        }
    }
}
