package ru.nsu.kotenkov.tree;

import java.util.*;

/**
 * Custom iterator class.
 *
 * @param <T> type of iterable elements
 */
class TreeIteratorDfs<T> implements Iterator<T> {
    private final Stack<Tree<T>> nodeStack = new Stack<>();

    /**
     * Class constructor that takes root node.
     *
     * @param node root
     */
    public TreeIteratorDfs(Tree<T> node) {
        node.setEdited(false);
        nodeStack.add(node);
    }

    /**
     * Finish iterations if we have no children left.
     *
     * @return true/false
     */
    @Override
    public boolean hasNext() {
        return !nodeStack.empty();
    }

    /**
     * Iterating through all children.
     *
     * @return T nodeName
     */
    @Override
    public T next() {
        Tree<T> returnNode = nodeStack.pop();
        if (returnNode.isEdited()) {
            throw new ConcurrentModificationException();
        }
        List<Tree<T>> children = returnNode.getChildren();
        for (Tree<T> child : children) {
            child.setEdited(false);
        }
        nodeStack.addAll(children);

        return returnNode.getNodeName();
    }

    /**
     * Correct way to remove something from iterable tree.
     */
    @Override
    public void remove() {
        nodeStack.pop();
    }
}
