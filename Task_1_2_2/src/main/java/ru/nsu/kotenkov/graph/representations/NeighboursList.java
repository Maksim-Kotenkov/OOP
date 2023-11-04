package ru.nsu.kotenkov.graph.representations;


import java.util.ArrayList;
import ru.nsu.kotenkov.graph.Edge;
import ru.nsu.kotenkov.graph.Node;


/**
 * Class for neighbours list.
 *
 * @param <T> type of nodeName
 */
public class NeighboursList<T> extends Matrix<T> {
    /**
     * String representation of the list of connected nodes.
     *
     * @return string-like list
     */
    @Override
    public String toString() {
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setIndex(i);
        }

        ArrayList<ArrayList<Node<T>>> neighbours = new ArrayList<>(nodeList.size());
        for (int i = 0; i < nodeList.size(); i++) {
            neighbours.add(new ArrayList<>());
        }
        for (Edge<T> edge : edgeList) {
            neighbours.get(edge.getFrom().getIndex()).add(edge.getTo());
        }

        String result = "";
        for (int i = 0; i < nodeList.size(); i++) {
            result = result.concat(nodeList.get(i).getNodeName().toString());
            result = result.concat(": ");
            for (Node<T> elem : neighbours.get(i)) {
                result = result.concat(elem.getNodeName().toString());
                result = result.concat(" ");
            }
            result = result.concat("\n");
        }

        for (Node<T> node : nodeList) {
            node.setIndex(-1);
        }

        return result;
    }
}
