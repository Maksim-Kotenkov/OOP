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
//        int numberOfExperiments = 11;
//        int[] sizes = new int[] {16000000, 17000000, 18000000, 19000000, 20000000, 21000000,
//                22000000, 23000000, 24000000, 25000000, 26000000};
        int numberOfExperiments = 3;
        int[] sizes = new int[] {3000000, 4000000, 5000000};

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
