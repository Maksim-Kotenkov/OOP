package ru.nsu.kotenkov.graph.representations;


import java.util.ArrayList;
import java.util.List;
import ru.nsu.kotenkov.graph.Edge;
import ru.nsu.kotenkov.graph.Node;


/**
 * Class of a matrix that we use for incidence and connectivity matrices.
 *
 * @param <T> type of nodeName
 */
public class Matrix<T> {
    /**
     * Lists of nodes and edges.
     */
    List<Node<T>> nodeList = new ArrayList<>();
    List<Edge<T>> edgeList = new ArrayList<>();

    /**
     * nodeList getter.
     *
     * @return nodeList
     */
    public List<Node<T>> getNodeList() {
        return nodeList;
    }

    /**
     * edgeList getter.
     *
     * @return edgeList
     */
    public List<Edge<T>> getEdgeList() {
        return edgeList;
    }

    /**
     * The method to add the node to the matrix.
     *
     * @param newNode the node we want to add
     */
    public void addNode(Node<T> newNode) {
        this.nodeList.add(newNode);
    }

    /**
     * The method to remove the node from the matrix.
     *
     * @param targetNode the node we want to remove
     */
    public void removeNode(Node<T> targetNode) {
        this.nodeList.remove(targetNode);
    }

    /**
     * The method to add the edge.
     *
     * @param newEdge the edge we want to add
     */
    public void addEdge(Edge<T> newEdge) {
        this.edgeList.add(newEdge);
    }

    /**
     * The method to remove the edge.
     *
     * @param targetEdge the edge we want to remove
     */
    public void removeEdge(Edge<T> targetEdge) {
        this.edgeList.remove(targetEdge);
    }
}
