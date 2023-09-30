package ru.nsu.kotenkov.tree;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class TreeTest {
    @Test
    @DisplayName("Check main")
    void checkMain() {
        Tree.main(new String[] {});
        assertTrue(true);
    }

    @Test
    @DisplayName("Check constructor")
    void checkInit() {
        Tree<String> tree = new Tree<>("R1");
        assertEquals("R1", tree.getNodeName());
    }

    @Test
    @DisplayName("Check child")
    void checkAddChild() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        assertEquals("A", a.getNodeName());
    }

    @Test
    @DisplayName("Tree string showcase with BFS")
    void checkBfsShowcase() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        String bfsResult = tree.printBFS();
        assertEquals("""
                                R1: A R2\s
                                A: B\s
                                R2: C D\s
                                """, bfsResult);
    }

    @Test
    @DisplayName("Tree string showcase with DFS")
    void checkDfsShowcase() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        String dfsResult = tree.printDFS();
        System.out.println(dfsResult);
        assertEquals("""
                                R1: (A: (B) R2: (C D))""", dfsResult);
    }
}
