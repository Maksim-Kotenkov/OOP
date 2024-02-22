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
     */
    public static void main(String[] args){
        int numberOfExperiments = 5;
        int[] sizes = new int[] {10000000, 16000000, 22000000, 26000000, 30000000};

        try {
            List<Map<Integer, Integer>> resultList = ExperimentalRun.run(numberOfExperiments, sizes,
                    3, true);
            // Statistics output
            DrawingCharts.draw("Results", resultList);
        } catch (IOException exception) {
            System.err.println("IO operation was interrupted: " + exception.getMessage());
        }
    }
}
