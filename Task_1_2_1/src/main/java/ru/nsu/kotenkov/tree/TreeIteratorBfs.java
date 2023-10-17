package ru.nsu.kotenkov.tree;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Custom iterator class.
 *
 * @param <T> type of iterable elements
 */
class TreeIteratorBfs<T> implements Iterator<T> {
    private final List<Tree<T>> nodeList = new ArrayList<>();
    private int nxt;

    /**
     * Class constructor that takes root node.
     *
     * @param node root
     */
    public TreeIteratorBfs(Tree<T> node) {
        node.setEdited(false);
        nodeList.add(node);
        nxt = 0;
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
        if (returnNode.isEdited()) {
            throw new ConcurrentModificationException();
        }
        List<Tree<T>> children = returnNode.getChildren();
        for (Tree<T> child : children) {
            child.setEdited(false);
        }
        nodeList.addAll(children);
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
