package ru.nsu.kotenkov.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Custom iterator class.
 *
 * @param <T> type of iterable elements
 */
public class TreeIterator<T> implements Iterator<T> {
    private final List<Tree<T>> nodeList = new ArrayList<>();
    private int nxt;

    /**
     * Class constructor that takes root node.
     *
     * @param node root
     */
    public TreeIterator(Tree<T> node) {
        nodeList.addAll(creation(node));
        nxt = 0;
    }

    /**
     * DFS-like creating collection of iterable nodes.
     *
     * @param node root
     * @return List of nodes
     */
    private List<Tree<T>> creation(Tree<T> node) {
        List<Tree<T>> returnList = new ArrayList<>();
        returnList.add(node);
        for (Tree<T> child : node.getChildren()) {
            returnList.addAll(creation(child));
        }

        return returnList;
    }

    /**
     * Finish iterations if we have no children left.
     *
     * @return true/false
     */
    @Override
    public boolean hasNext() {
        return nxt < nodeList.size();
    }

    /**
     * Iterating through all children.
     *
     * @return T nodeName
     */
    @Override
    public T next() {
        Tree<T> returnNode = nodeList.get(nxt);
        nxt += 1;

        return returnNode.getNodeName();
    }

    /**
     * Correct way to remove something from iterable tree.
     */
    @Override
    public void remove() {
        nodeList.remove(nxt - 1);
        nxt -= 1;
    }
}
