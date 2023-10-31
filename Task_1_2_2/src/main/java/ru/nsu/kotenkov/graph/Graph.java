package ru.nsu.kotenkov.graph;


import java.util.List;
import ru.nsu.kotenkov.graph.representations.ConnectivityMatrix;
import ru.nsu.kotenkov.graph.representations.IncidenceMatrix;
import ru.nsu.kotenkov.graph.representations.NeighboursList;


/**
 * Graph class.
 *
 * @param <T> nodeName type
 */
public class Graph<T> {
    /**
     * Our graph contains connectivity and incidence matrix representations
     * and neighbours list representation too.
     */
    private final ConnectivityMatrix<T> connectivityMatrix = new ConnectivityMatrix<>();
    private final IncidenceMatrix<T> incidenceMatrix = new IncidenceMatrix<>();
    private final NeighboursList<T> neighboursList = new NeighboursList<>();

    @Override
    public String toString() {
        return this.getConnectivityMatrix();
    }

    /**
     * To create a graph only one node is needed.
     *
     * @param startNode the node we start building graph from
     */
    public Graph(Node<T> startNode) {
        this.connectivityMatrix.addNode(startNode);
        this.incidenceMatrix.addNode(startNode);
        this.neighboursList.addNode(startNode);
    }

    /**
     * Getter for nodeList.
     *
     * @return List of Node T
     */
    public List<Node<T>> getNodeList() {
        return this.connectivityMatrix.getNodeList();
    }

    /**
     * Getter for edgeList.
     *
     * @return List of Edge T
     */
    public List<Edge<T>> getEdgeList() {
        return this.connectivityMatrix.getEdgeList();
    }

    /**
     * To add a node we need to add this node to all representations.
     *
     * @param newNode the node we want to add to the graph
     */
    public void addNode(Node<T> newNode) {
        this.connectivityMatrix.addNode(newNode);
        this.incidenceMatrix.addNode(newNode);
        this.neighboursList.addNode(newNode);
    }

    /**
     * To remove a node from the graph we need to remove this node from all representations.
     *
     * @param targetNode the node we want to remove
     */
    public void removeNode(Node<T> targetNode) {
        this.connectivityMatrix.removeNode(targetNode);
        this.incidenceMatrix.removeNode(targetNode);
        this.neighboursList.removeNode(targetNode);
    }

    /**
     * To add an edge we need to add this edge to all representations.
     *
     * @param newEdge the edge we add
     */
    public void addEdge(Edge<T> newEdge) {
        this.connectivityMatrix.addEdge(newEdge);
        this.incidenceMatrix.addEdge(newEdge);
        this.neighboursList.addEdge(newEdge);
    }

    /**
     * To remove an edge from the graph we need to remove this edge from all representations.
     *
     * @param targetEdge the edge we remove
     */
    public void removeEdge(Edge<T> targetEdge) {
        this.connectivityMatrix.removeEdge(targetEdge);
        this.incidenceMatrix.removeEdge(targetEdge);
        this.neighboursList.removeEdge(targetEdge);
    }

    /**
     * String representation in the connectivity matrix form.
     *
     * @return string-like connectivity matrix
     */
    public String getConnectivityMatrix() {
        return this.connectivityMatrix.toString();
    }

    /**
     * String representation in the incidence matrix form.
     *
     * @return string-like incidence matrix
     */
    public String getIncidenceMatrix() {
        return this.incidenceMatrix.toString();
    }

    /**
     * Connectivity matrix int[][] representation for system purposes.
     * As an example, for finding paths.
     *
     * @return int[i][j] = weight of the edge between ith and jth nodes
     */
    int[][] getConnectivityMatrixSystem() {
        return this.connectivityMatrix.updateConnectivityMatrix();
    }

    /**
     * Incidence matrix int[][] representation for system purposes.
     * As an example, for finding paths.
     *
     * @return int[i][j] = -weight if jth edge goes from ith edge
     *                     +weight if it goes to ith edge.
     */
    int[][] getIncidenceMatrixSystem() {
        return this.incidenceMatrix.updateIncidenceMatrix();
    }


    /**
     * String representation in the neighbours list form.
     *
     * @return string-like neighbours list
     */
    public String getNeighboursList() {
        return this.neighboursList.toString();
    }
}
