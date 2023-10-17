package ru.nsu.kotenkov.tree;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


/**
 * Tree class.
 *
 * @param <T> - type of nodes in our Tree.
 */
public class Tree<T> implements Iterable<T> {

    /**
     * Children, nodeName, ancestor and a flag for being changed.
     *
     */
    private List<Tree<T>> children = new ArrayList<>();
    private final T nodeName;
    private Tree<T> ancestor;
    private boolean edited = false;

    /**
     * enum for changing type of iterating through the object
     */
    public enum IteratorTypesEnum
    {
        BFS,
        DFS
    };
    private IteratorTypesEnum iteratorType;

    /**
     * Class constructor for initializing nodeName.
     *
     * @param root - T nodeName value.
     */
    public Tree(T root) {
        this.nodeName = root;
        this.ancestor = null;
        this.iteratorType = IteratorTypesEnum.BFS;
    }

    @Override
    public String toString() {
        return this.printBfs();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tree<?>)) {
            return false;
        }
        if (nodeName != ((Tree<?>) o).getNodeName()) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nodeName);
        for (Tree<?> child : children) {
            result += child.hashCode();
        }

        return result;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setIteratorType(IteratorTypesEnum iteratorType) {
        this.iteratorType = iteratorType;
    }

    /**
     * Public method for accessing nodeName.
     *
     * @return - T nodeName value.
     */
    public T getNodeName() {
        return this.nodeName;
    }

    /**
     * Public method for accessing list of children.
     *
     * @return List children
     */
    public List<Tree<T>> getChildren() {
        return children;
    }

    /**
     * Public method for setting children list.
     *
     * @param children List new children
     */
    public void setChildren(List<Tree<T>> children) {
        this.children = children;
        this.edited = true;
    }

    /**
     * Public method for accessing ancestor node.
     *
     * @return Tree T ancestor node
     */
    public Tree<T> getAncestor() {
        return ancestor;
    }

    /**
     * Public method for setting new ancestor node.
     *
     * @param ancestor - new Tree T ancestor node
     */
    public void setAncestor(Tree<T> ancestor) {
        this.ancestor = ancestor;
        this.edited = true;
    }

    /**
     * Creating a child and adding it to our children list.
     *
     * @param childName - nodeName of child node.
     * @return - child Tree T object.
     */
    public Tree<T> addChild(T childName) {
        Tree<T> child = new Tree<>(childName);
        this.children.add(child);
        child.setAncestor(this);
        this.edited = true;

        return child;
    }

    /**
     * Another version of addChild for connecting our tree with already
     *  created subtree. We don't need to return object, because this object was already created
     *  and was given to our method.
     *
     * @param child - subtree object.
     */
    public void addChild(Tree<T> child) {
        this.children.add(child);
        child.setAncestor(this);

        this.edited = true;
    }

    /**
     * Remove this node object from children list of the ancestor.
     *
     */
    public void remove() {
        if (ancestor != null) {
            for (Tree<T> child : this.children) {
                child.ancestor.setAncestor(this.ancestor);
            }
            this.ancestor.children.remove(this);
            this.ancestor.children.addAll(this.children);

            this.edited = true;
        }
    }

    /**
     * BFS with collecting all nodes into String.
     *
     * @return - String view of the tree.
     */
    public String printBfs() {
        String result = "";
        if (children.isEmpty()) {
            return result;
        }

        result = result.concat(nodeName.toString());
        result = result.concat(": ");
        for (Tree<T> child : children) {
            result = result.concat(child.getNodeName().toString());
            result = result.concat(" ");
        }

        result = result.concat("\n");
        for (Tree<T> child : children) {
            result = result.concat(child.printBfs());
        }

        return result;
    }

    /**
     * DFS with collecting all nodes into String.
     *
     * @return - String view of the tree.
     */
    public String printDfs() {
        String result = this.nodeName.toString();
        if (children.isEmpty()) {
            return result;
        }

        result = result.concat(": (");
        for (Tree<T> child : children) {
            result = result.concat(child.printDfs());
            result = result.concat(" ");
        }
        result = result.substring(0, result.length() - 1);
        result = result.concat(")");

        return result;
    }

    /**
     * Main without any purpose.
     *
     * @param args - basic signature arguments.
     */
    public static void main(String[] args) {
        System.out.println("Main goes brr");
    }

    /**
     * Return custom Iterator object for this class.
     *
     * @return - TreeIterator object
     */
    @Override
    public Iterator<T> iterator() {
        if (this.iteratorType == IteratorTypesEnum.BFS) {
            return new TreeIteratorBfs<>(this);
        }
        return new TreeIteratorDfs<>(this);
    }
}
