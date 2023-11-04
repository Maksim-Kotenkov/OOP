package ru.nsu.kotenkov.graph.representations;


import ru.nsu.kotenkov.graph.Edge;
import ru.nsu.kotenkov.graph.Node;

/**
 * Connectivity matrix class with T type nodeNames.
 *
 * @param <T> - nodeName type
 */
public class ConnectivityMatrix<T> extends Matrix<T> {
    /**
     * A method to fill connectivity matrix according to the graph.
     *
     * @return int[i][j] = weight of the edge between ith and jth node or 0 if there is no edge
     */
    public int[][] updateConnectivityMatrix() {
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setIndex(i);
        }

        int[][] matrix = new int[nodeList.size()][nodeList.size()];
        for (Edge<T> edge : super.edgeList) {
            if (edge.getTo().getIndex() != -1 && edge.getFrom().getIndex() != -1) {
                matrix[edge.getFrom().getIndex()][edge.getTo().getIndex()] = edge.getWeight();
            }
        }

        return matrix;
    }

    /**
     * String representation of int[][] connectivity matrix.
     *
     * @return string-like result of int[][]
     */
    @Override
    public String toString() {
        int[][] matrix = updateConnectivityMatrix();

        String result = "  | ";
        for (Node<T> node : nodeList) {
            result = result.concat(node.getNodeName().toString());
            result = result.concat(" | ");
        }
        result = result.concat("\n");

        for (int i = 0; i < nodeList.size(); i++) {
            result = result.concat(nodeList.get(i).getNodeName().toString());
            result = result.concat(" | ");
            for (int elem : matrix[i]) {
                result = result.concat(String.valueOf(elem));
                result = result.concat(" | ");
            }
            result = result.concat("\n");
        }

        for (Node<T> node : nodeList) {
            node.setIndex(-1);
        }

        return result;
    }
}
