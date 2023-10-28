package ru.nsu.kotenkov.tree;

import java.util.*;

/**
 * Custom iterator class.
 *
 * @param <T> type of iterable elements
 */
class TreeIteratorDfs<T> implements Iterator<T> {
    private final Stack<Tree<T>> nodeStack = new Stack<>();
    private final Stack<Integer> expectedModCount = new Stack<>();

    /**
     * Class constructor that takes root node.
     *
     * @param node root
     */
    public TreeIteratorDfs(Tree<T> node) {
        expectedModCount.add(node.getModCount());
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
        Stack<Tree<T>> nodeStackCopy = (Stack<Tree<T>>) nodeStack.clone();
        Stack<Tree<T>> nodeStackReversed = new Stack<>();
        while (!nodeStackCopy.empty()) {
            nodeStackReversed.add(nodeStackCopy.pop());
        }

        Stack<Integer> actualModCount = new Stack<>();
        while (!nodeStackReversed.empty()) {
            actualModCount.add(nodeStackReversed.pop().getModCount());
        }
        if (!actualModCount.equals(expectedModCount)) {
            throw new ConcurrentModificationException();
        }

        Tree<T> returnNode = nodeStack.pop();
        expectedModCount.pop();
        List<Tree<T>> children = returnNode.getChildren();

        for (Tree<T> child : children) {
            nodeStack.add(child);
            expectedModCount.add(child.getModCount());
        }

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
