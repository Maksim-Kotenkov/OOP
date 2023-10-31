package ru.nsu.kotenkov.graph;


import java.util.Arrays;
import java.util.List;


/**
 * Class to find lengths of shortest paths.
 *
 * @param <T> type of nodeName
 */
public class DijkstraPathfinder<T> {
    /**
     * Enum for types of matrices.
     */
    public enum StoringTypes {
        CONNECTIVITY,
        INCIDENCE
    }

    /**
     * weights - Connectivity matrix.
     * nodeList - list of nodes for node indexes.
     * StoringTypes - wht kind of matrix should we use for initializing.
     */
    private int[][] weights;
    private final List<Node<T>> nodeList;

    /**
     * DijkstraPathfinder object should be created for one graph.
     * Constructor fills weights and nodeList according to the target
     * graph and selected storing type.
     *
     * @param inputGraph target graph
     * @param type what kind of matrix should we use for initializing
     */
    public DijkstraPathfinder(Graph<T> inputGraph, StoringTypes type) {
        if (type.equals(StoringTypes.CONNECTIVITY)) {
            weights = inputGraph.getConnectivityMatrixSystem();
        } else {
            weights = inputGraph.getIncidenceMatrixSystem();
            int[][] newWeights = new int[weights.length][weights.length];
            int start = -1;
            int finish = -1;
            int val = 0;
            for (int edge = 0; edge < weights[0].length; edge++) {
                for (int node = 0; node < weights.length; node++) {
                    if (weights[node][edge] < 0) {
                        start = node;
                    } else if (weights[node][edge] > 0) {
                        finish = node;
                        val = weights[node][edge];
                    }
                    if (start != -1 && finish != -1) {
                        break;
                    }
                }
                newWeights[start][finish] = val;
                start = -1;
                finish = -1;
            }
            weights = newWeights;
        }

        nodeList = inputGraph.getNodeList();
    }

    /**
     * String representation of computed paths.
     *
     * @param beginIndex the node we start from
     * @return String-like list
     */
    public String paths(int beginIndex) {
        String result = "Paths from ";
        result = result.concat(nodeList.get(beginIndex).getNodeName().toString());
        result = result.concat(":\n");
        int[] matrix = this.compute(beginIndex);

        for (int i = 0; i < matrix.length; i++) {
            if (i != beginIndex) {
                result = result.concat(nodeList.get(i).getNodeName().toString());
                result = result.concat(": ");
                result = result.concat(String.valueOf(matrix[i]));
                result = result.concat("\n");
            }
        }

        return result;
    }

    /**
     * The computation of all shortest paths from node beginIndex to every other node.
     * If some node can not be accessed, -1 is placed in the list.
     *
     * @param beginIndex the node we begin from
     * @return int[i] - the length of the shortest path from beginIndex to ith node
     */
    public int[] compute(int beginIndex) {
        int[] result = new int[nodeList.size()];
        boolean[] visited = new boolean[nodeList.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        result[beginIndex] = 0;

        int minIndex;
        do {
            minIndex = 10000;
            int min = 10000;
            for (int i = 0; i < result.length; i++) {
                if (!visited[i] && result[i] < min) {
                    min = result[i];
                    minIndex = i;
                }
            }

            if (minIndex != 10000) {
                for (int i = 0; i < weights.length; i++) {
                    if (weights[minIndex][i] > 0) {
                        int temp = min + weights[minIndex][i];
                        if (temp < result[i]) {
                            result[i] = temp;
                        }
                    }
                }
                visited[minIndex] = true;
            }
        } while (minIndex < 10000);

        for (int i = 0; i < result.length; i++) {
            if (result[i] == Integer.MAX_VALUE) {
                result[i] = -1;
            }
        }

        return result;
    }
}
