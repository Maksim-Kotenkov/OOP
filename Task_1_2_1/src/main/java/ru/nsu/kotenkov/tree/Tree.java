package ru.nsu.kotenkov.tree;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Tree class.
 *
 * @param <T> - type of nodes in our Tree.
 */
public class Tree<T> {

    /**
     * Children, nodeName and ancestor.
     *
     */
    private List<Tree<T>> children = new ArrayList<>();
    private final T nodeName;
    private Tree<T> ancestor;

    /**
     * Class constructor for initializing nodeName.
     *
     * @param root - T nodeName value.
     */
    public Tree(T root) {
        this.nodeName = root;
        this.ancestor = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tree<?>)) {
            return false;
        }
        if ((ancestor == null && ((Tree<?>) o).getAncestor() != null)
                || (ancestor != null && ((Tree<?>) o).getAncestor() == null)) {
            return false;
        }
        if (nodeName != ((Tree<?>) o).getNodeName()) {
            return false;
        }
        if (ancestor != null && ancestor.getNodeName() != ((Tree<?>) o).ancestor.getNodeName()) {
            return false;
        }
        return (this.hashCode() == o.hashCode());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nodeName);
        if (ancestor != null) {
            result += Objects.hash(ancestor.getNodeName());
        }
        for (Tree<?> child : children) {
            result += child.hashCode();
        }

        return result;
    }

    /**
     * Public method for accessing nodeName.
     *
     * @return - T nodeName value.
     */
    public T getNodeName() {
        return this.nodeName;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public Tree<T> getAncestor() {
        return ancestor;
    }

    public void setAncestor(Tree<T> ancestor) {
        this.ancestor = ancestor;
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
    }


    /**
     * Remove this node object from children list of the ancestor;
     */
    public void remove() {
        List<Tree<T>> newChildren = this.ancestor.getChildren();
        newChildren.remove(this);
        this.ancestor.setChildren(newChildren);
    }

    /**
     * BFS with collecting all nodes into String.
     *
     * @return - String view of the tree.
     */
    public String printBFS() {
        String result = "";
        if (this.children.isEmpty()) {
            return result;
        }

        result = result.concat(this.nodeName.toString());
        result = result.concat(": ");
        for (Tree<T> child : this.children) {
            result = result.concat(child.getNodeName().toString());
            result = result.concat(" ");
        }

        result = result.concat("\n");
        for (Tree<T> child : this.children) {
            result = result.concat(child.printBFS());
        }

        return result;
    }

    /**
     * DFS with collecting all nodes into String.
     *
     * @return - String view of the tree.
     */
    public String printDFS() {
        String result = this.nodeName.toString();
        if (this.children.isEmpty()) {
            return result;
        }

        result = result.concat(": (");
        for (Tree<T> child : this.children){
            result = result.concat(child.printDFS());
            result = result.concat(" ");
        }
        result = result.substring(0, result.length()-1);
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
}
