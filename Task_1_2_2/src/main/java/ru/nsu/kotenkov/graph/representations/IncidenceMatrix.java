package ru.nsu.kotenkov.graph.representations;


import ru.nsu.kotenkov.graph.Edge;
import ru.nsu.kotenkov.graph.Node;

/**
 * Class of incidence matrix.
 *
 * @param <T> type of nodeName
 */
public class IncidenceMatrix<T> extends Matrix<T> {
    /**
     * A method to fill int[][] with values according to incident nodes/edges.
     *
     * @return int[i][j] = -weight if jth edge goes from ith edge
     *                      +weight if it goes to ith edge.
     */
    public int[][] updateIncidenceMatrix() {
        for (int i = 0; i < edgeList.size(); i++) {
            edgeList.get(i).setIndex(i);
        }

        int[][] matrix = new int[nodeList.size()][edgeList.size()];
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setIndex(i);
        }
        for (Edge<T> edge : super.edgeList) {
            if (edge.getTo().getIndex() != -1 && edge.getFrom().getIndex() != -1) {
                matrix[edge.getFrom().getIndex()][edge.getIndex()] = -edge.getWeight();
                matrix[edge.getTo().getIndex()][edge.getIndex()] = edge.getWeight();
            }
        }

        return matrix;
    }

    /**
     * String representation of int[][] incidence matrix.
     *
     * @return String result
     */
    @Override
    public String toString() {
        int[][] matrix = updateIncidenceMatrix();

        String result = "  | ";
        for (Edge<T> edge : edgeList) {
            result = result.concat(String.valueOf(edge.getIndex()));
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
