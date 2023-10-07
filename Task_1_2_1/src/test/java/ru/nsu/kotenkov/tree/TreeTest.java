package ru.nsu.kotenkov.tree;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        a.addChild("B");
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
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        String dfsResult = tree.printDFS();
        assertEquals("""
                                R1: (A: (B) R2: (C D))""", dfsResult);
    }

    @Test
    @DisplayName("Tree string showcase with DFS after remove(B)")
    void checkRemoveNode() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        b.remove();

        String dfsResult = tree.printDFS();
        assertEquals("""
                                R1: (A R2: (C D))""", dfsResult);
    }

    @Test
    @DisplayName("Tree string showcase with DFS after remove(R2)")
    void checkRemoveSubtree() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        subtree.remove();

        String dfsResult = tree.printDFS();
        assertEquals("""
                                R1: (A: (B))""", dfsResult);
    }

    @Test
    @DisplayName("Check equals: true")
    void checkEqualsTrueRoots() {
        Tree<String> tree1 = new Tree<>("R1");

        Tree<String> tree2 = new Tree<>("R1");

        assertEquals(tree1, tree2);
    }

    @Test
    @DisplayName("Check equals: true")
    void checkEqualsTrueSimple() {
        Tree<String> tree1 = new Tree<>("R1");
        tree1.addChild("A");
        Tree<String> tree2 = new Tree<>("R1");
        tree2.addChild("A");

        assertEquals(tree1, tree2);
    }

    @Test
    @DisplayName("Check equals: false")
    void checkEqualsFalseSimple() {
        Tree<String> tree1 = new Tree<>("R1");
        tree1.addChild("A");
        Tree<String> tree2 = new Tree<>("R1");
        tree2.addChild("B");

        if (tree1.equals(tree2)) {
            fail();
        } else {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Check equals: true with many nodes")
    void checkEqualsTrueComplicated() {
        Tree<String> tree1 = new Tree<>("R1");
        var a1 = tree1.addChild("A");
        a1.addChild("B");
        Tree<String> subtree1 = new Tree<>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        Tree<String> tree2 = new Tree<>("R1");
        var a2 = tree2.addChild("A");
        a2.addChild("B");
        Tree<String> subtree2 = new Tree<>("R2");

        subtree2.addChild("D");
        tree2.addChild(subtree2);
        subtree2.addChild("C");

        assertEquals(tree1, tree2);
    }

    @Test
    @DisplayName("Check equals: false with many nodes")
    void checkEqualsFalseComplicated() {
        Tree<String> tree1 = new Tree<>("R1");
        var a1 = tree1.addChild("A");
        a1.addChild("B");
        Tree<String> subtree1 = new Tree<>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        tree1.addChild(subtree1);

        Tree<String> tree2 = new Tree<>("R1");
        var a2 = tree2.addChild("A");
        a2.addChild("B");
        Tree<String> subtree2 = new Tree<>("R2");

        subtree2.addChild("D");
        tree2.addChild(subtree2);
        subtree2.addChild("CC");

        if (tree1.equals(tree2)) {
            fail();
        } else {
            assertTrue(true);
        }
    }
}
