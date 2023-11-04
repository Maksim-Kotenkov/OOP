package ru.nsu.kotenkov.graph;


/**
 * Class for nodes.
 *
 * @param <T> nodeName type
 */
public class Node<T> {
    private T nodeName;
    private int index = -1;

    /**
     * Constructor.
     *
     * @param nodeName the name of the node
     */
    public Node(T nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * nodeName getter.
     *
     * @return T nodeName
     */
    public T getNodeName() {
        return nodeName;
    }

    /**
     * nodeName setter.
     *
     * @param nodeName T nodeName
     */
    public void setNodeName(T nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * Index getter.
     *
     * @return int index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Index setter.
     *
     * @param index int index
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
