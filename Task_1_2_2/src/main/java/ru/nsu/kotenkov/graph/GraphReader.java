package ru.nsu.kotenkov.graph;


import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


/**
 * Class for initializing a graph from txt file.
 * Initialization format:
 *     First line: Nodes
 *     Next lines: edges in format: start node index, finish node index, weight
 * Example:
 *     S A B
 *     0 1 8
 *     0 2 3
 *     2 1 2
 */
public class GraphReader {
    /**
     * File that we need to read.
     */
    private final String filename;

    /**
     * Constructor, that initializes the filename.
     *
     * @param path name of the file with .txt
     */
    public GraphReader(String path) {
        this.filename = path;
    }

    /**
     * A method that parses the data in the file and create a Graph object.
     *
     * @return graph object
     */
    public Graph<String> read() {
        Graph<String> graph;
        try (InputStream file = this.getClass().getResourceAsStream("/" + this.filename)) {
            assert file != null;
            Scanner scanner = new Scanner(file);
            graph = new Graph<>();

            while (!scanner.hasNextInt()) {
                graph.addNode(new Node<>(scanner.next()));
            }

            while (scanner.hasNextInt()) {
                graph.addEdge(new Edge<>(graph.getNodeList().get(scanner.nextInt()),
                        graph.getNodeList().get(scanner.nextInt()),
                        scanner.nextInt()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return graph;

    }
}
