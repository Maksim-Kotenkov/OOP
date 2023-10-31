package ru.nsu.kotenkov.graph;


/**
 * Edge class.
 *
 * @param <T> nodeName type
 */
public class Edge<T> {
    private final int weight;
    private int index = -1;
    private final Node<T> from;
    private final Node<T> to;

    /**
     * Edge constructor.
     *
     * @param from node from what the edge goes
     * @param to node to what the edge goes
     * @param weight weight of the edge
     */
    public Edge(Node<T> from, Node<T> to, int weight) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    /**
     * Getter for from.
     *
     * @return Node T from
     */
    public Node<T> getFrom() {
        return from;
    }

    /**
     * Getter for To.
     *
     * @return Node T to
     */
    public Node<T> getTo() {
        return to;
    }

    /**
     * Getter for weight.
     *
     * @return int weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Setter for index.
     *
     * @param index int index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter for index.
     *
     * @return in index
     */
    public int getIndex() {
        return index;
    }
}
