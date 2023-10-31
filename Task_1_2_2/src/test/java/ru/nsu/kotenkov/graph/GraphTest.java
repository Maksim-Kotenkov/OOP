package ru.nsu.kotenkov.graph;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester class.
 */
public class GraphTest {

    @Test
    @DisplayName("Check init")
    void checkInit() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);

        assertEquals(new ArrayList<>(Arrays.asList(startNode)), graph.getNodeList());
    }

    @Test
    @DisplayName("Check addNode")
    void checkAddChild() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);

        assertEquals(new ArrayList<>(Arrays.asList(startNode, newNode)), graph.getNodeList());
    }

    @Test
    @DisplayName("Check removeNode")
    void checkRemoveChild() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        graph.removeNode(startNode);

        assertEquals(new ArrayList<>(), graph.getNodeList());
    }

    @Test
    @DisplayName("Check addEdge")
    void checkAddEdge() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);

        assertEquals(new ArrayList<>(Arrays.asList(edge)), graph.getEdgeList());
        assertEquals(startNode, edge.getFrom());
        assertEquals(newNode, edge.getTo());
        assertEquals(8, edge.getWeight());
    }

    @Test
    @DisplayName("Check removeEdge")
    void checkRemoveEdge() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        graph.removeEdge(edge);

        assertEquals(new ArrayList<>(), graph.getEdgeList());
    }

    @Test
    @DisplayName("Connectivity matrix check")
    void checkConnectivityMatrix() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        System.out.println(graph.getConnectivityMatrix());
        assertEquals("  | S | N | A | B | \n"
                + "S | 0 | 8 | 3 | 0 | \n"
                + "N | 0 | 0 | 0 | 0 | \n"
                + "A | 0 | 0 | 0 | 0 | \n"
                + "B | 0 | 0 | 20 | 0 | \n", graph.getConnectivityMatrix());

    }

    @Test
    @DisplayName("Connectivity matrix with remove check")
    void checkConnectivityMatrixRemove() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        graph.removeEdge(edge);

        System.out.println(graph.getConnectivityMatrix());
        assertEquals("  | S | N | A | B | \n"
                + "S | 0 | 0 | 3 | 0 | \n"
                + "N | 0 | 0 | 0 | 0 | \n"
                + "A | 0 | 0 | 0 | 0 | \n"
                + "B | 0 | 0 | 20 | 0 | \n", graph.getConnectivityMatrix());
    }

    @Test
    @DisplayName("Connectivity matrix with non-existing node check")
    void checkConnectivityMatrixFailure() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        graph.removeNode(newNode);

        System.out.println(graph.getConnectivityMatrix());
        assertEquals("  | S | A | B | \n"
                + "S | 0 | 3 | 0 | \n"
                + "A | 0 | 0 | 0 | \n"
                + "B | 0 | 20 | 0 | \n", graph.getConnectivityMatrix());
    }

    @Test
    @DisplayName("Incidence matrix check")
    void checkIncidenceMatrix() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        System.out.println(graph.getIncidenceMatrix());
        assertEquals("  | 0 | 1 | 2 | \n"
                + "S | -8 | -3 | 0 | \n"
                + "N | 8 | 0 | 0 | \n"
                + "A | 0 | 3 | 20 | \n"
                + "B | 0 | 0 | -20 | \n", graph.getIncidenceMatrix());

    }

    @Test
    @DisplayName("Incidence matrix with remove check")
    void checkIncidenceMatrixRemove() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        graph.removeEdge(edge);

        System.out.println(graph.getIncidenceMatrix());
        assertEquals("  | 0 | 1 | \n"
                + "S | -3 | 0 | \n"
                + "N | 0 | 0 | \n"
                + "A | 3 | 20 | \n"
                + "B | 0 | -20 | \n", graph.getIncidenceMatrix());
    }

    @Test
    @DisplayName("Neighbours list check")
    void checkList() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        System.out.println(graph.getNeighboursList());
        assertEquals("S: N A \n"
                + "N: \n"
                + "A: \n"
                + "B: A \n", graph.getNeighboursList());

    }

    @Test
    @DisplayName("Neighbours list with remove check")
    void checkListRemove() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        graph.removeEdge(edge3);

        System.out.println(graph.getNeighboursList());
        assertEquals("S: N A \n"
                + "N: \n"
                + "A: \n"
                + "B: \n", graph.getNeighboursList());

    }

    @Test
    @DisplayName("Connectivity Dijkstra check")
    void checkDijkstra() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        long startTime = System.nanoTime();

        DijkstraPathfinder<String> pathfinder = new DijkstraPathfinder<>(graph,
                DijkstraPathfinder.StoringTypes.CONNECTIVITY);
        String path = pathfinder.paths(0);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("CONNECTIVITY DIJKSTRA");
        System.out.println(path + duration + "ms\n");
        assertEquals("Paths from S:\n"
                + "N: 8\n"
                + "A: 3\n"
                + "B: -1\n", path);
    }

    @Test
    @DisplayName("Connectivity Dijkstra check 2")
    void checkDijkstraComplex() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);
        Edge<String> edge4 = new Edge<>(newNode2, newNode3, 20);
        graph.addEdge(edge4);
        Edge<String> edge5 = new Edge<>(newNode2, newNode, 2);
        graph.addEdge(edge5);

        long startTime = System.nanoTime();

        DijkstraPathfinder<String> pathfinder = new DijkstraPathfinder<>(graph,
                DijkstraPathfinder.StoringTypes.CONNECTIVITY);
        String path = pathfinder.paths(0);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("CONNECTIVITY DIJKSTRA");
        System.out.println(path + duration + "ms\n");
        assertEquals("Paths from S:\n"
                + "N: 5\n"
                + "A: 3\n"
                + "B: 23\n", path);
    }

    @Test
    @DisplayName("Incidence Dijkstra check")
    void checkDijkstraIncidence() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);

        long startTime = System.nanoTime();

        DijkstraPathfinder<String> pathfinder = new DijkstraPathfinder<>(graph,
                DijkstraPathfinder.StoringTypes.INCIDENCE);
        String path = pathfinder.paths(0);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INCIDENCE DIJKSTRA");
        System.out.println(path + duration + "ms\n");
        assertEquals("Paths from S:\n"
                + "N: 8\n"
                + "A: 3\n"
                + "B: -1\n", path);
    }

    @Test
    @DisplayName("Incidence Dijkstra check 2")
    void checkDijkstraIncidenceComplex() {
        Node<String> startNode = new Node<>("S");
        Graph<String> graph = new Graph<>(startNode);
        Node<String> newNode = new Node<>("N");
        graph.addNode(newNode);
        Edge<String> edge = new Edge<>(startNode, newNode, 8);
        graph.addEdge(edge);
        Node<String> newNode2 = new Node<>("A");
        graph.addNode(newNode2);
        Node<String> newNode3 = new Node<>("B");
        graph.addNode(newNode3);
        Edge<String> edge2 = new Edge<>(startNode, newNode2, 3);
        graph.addEdge(edge2);
        Edge<String> edge3 = new Edge<>(newNode3, newNode2, 20);
        graph.addEdge(edge3);
        Edge<String> edge4 = new Edge<>(newNode2, newNode3, 20);
        graph.addEdge(edge4);
        Edge<String> edge5 = new Edge<>(newNode2, newNode, 2);
        graph.addEdge(edge5);

        long startTime = System.nanoTime();

        DijkstraPathfinder<String> pathfinder = new DijkstraPathfinder<>(graph,
                DijkstraPathfinder.StoringTypes.INCIDENCE);
        String path = pathfinder.paths(0);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INCIDENCE DIJKSTRA");
        System.out.println(path + duration + "ms\n");
        assertEquals("Paths from S:\n"
                + "N: 5\n"
                + "A: 3\n"
                + "B: 23\n", path);
    }

    @Test
    @DisplayName("File check easy")
    void checkWithFile() {
        GraphReader reader = new GraphReader("inpEasy.txt");
        Graph<String> graph = reader.read();

        long startTime = System.nanoTime();

        DijkstraPathfinder<String> pathfinder = new DijkstraPathfinder<>(graph,
                DijkstraPathfinder.StoringTypes.INCIDENCE);
        String path = pathfinder.paths(0);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INCIDENCE DIJKSTRA w file");
        System.out.println(path + duration + "ms\n");
        assertEquals("Paths from S:\n"
                + "N: 5\n"
                + "A: 3\n"
                + "B: 23\n", path);
    }

    @Test
    @DisplayName("File check complicated")
    void checkWithFile2() {
        GraphReader reader = new GraphReader("inpComplicated.txt");
        Graph<String> graph = reader.read();

        long startTime = System.nanoTime();

        DijkstraPathfinder<String> pathfinder = new DijkstraPathfinder<>(graph,
                DijkstraPathfinder.StoringTypes.INCIDENCE);
        String path = pathfinder.paths(0);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("INCIDENCE DIJKSTRA w file");
        System.out.println(path + duration + "ms\n");
        assertEquals("Paths from S:\n"
                + "A: 8\n"
                + "B: 18\n"
                + "C: 40\n"
                + "D: 2\n"
                + "E: 4\n"
                + "F: 6\n"
                + "G: 5\n", path);
    }
}
