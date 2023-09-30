package ru.nsu.kotenkov.tree;


import java.util.ArrayList;
import java.util.List;


/**
 * Tree class.
 *
 * @param <T> - type of nodes in our Tree.
 */
public class Tree<T> {

    /**
     * Children and nodeName should not be accessible from outer program;
     */
    private final List<Tree<T>> children = new ArrayList<>();
    private final T nodeName;

    /**
     * Class constructor for initializing nodeName.
     *
     * @param root - T nodeName value.
     */
    public Tree(T root) {
        this.nodeName = root;
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
     * Creating a child and adding it to our children list.
     *
     * @param childName - nodeName of child node.
     * @return - child Tree T object.
     */
    public Tree<T> addChild(T childName) {
        Tree<T> child = new Tree<> (childName);
        this.children.add(child);

        return child;
    }

    /**
     * Another version of addChild for connecting our tree with already
     *  created subtree. We don't need to return object, because this object was already created
     *  and was given to our method.
     *
     * @param childName - subtree object.
     */
    public void addChild(Tree<T> childName) {
        this.children.add(childName);
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