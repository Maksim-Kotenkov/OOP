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
    private final List<Integer> expectedModCount = new ArrayList<>();

    /**
     * Class constructor that takes root node.
     *
     * @param node root
     */
    public TreeIteratorBfs(Tree<T> node) {
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
        List<Integer> actualModCount = new ArrayList<>();
        for (int i = 0; i <= nxt; i++) {
            actualModCount.add(nodeList.get(i).getModCount());
        }

        Tree<T> returnNode = nodeList.get(nxt);
        List<Tree<T>> children = returnNode.getChildren();
        expectedModCount.add(returnNode.getModCount());

        if (!actualModCount.equals(expectedModCount)) {
            throw new ConcurrentModificationException();
        }

        nodeList.addAll(children);
        nxt++;

        return returnNode.getNodeName();
    }

    /**
     * Correct way to remove something from iterable tree.
     */
    @Override
    public void remove() {
        nodeList.remove(nxt - 1);
        expectedModCount.remove(nxt - 1);
        nxt -= 1;
    }
}
