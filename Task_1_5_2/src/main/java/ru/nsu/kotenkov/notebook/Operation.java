package ru.nsu.kotenkov.notebook;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


/**
 * Operation enum, that connects string form of a command with operation object.
 */
public enum Operation {
    ADD(2) {
        @Override
        void action(List<String> args) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.readValue(Paths.get("notebook.json").toFile(), ObjectNode.class);
//            ObjectNode root = mapper.createObjectNode();
            root.put(args.get(0), args.get(1));

            mapper.writeValue(Paths.get("notebook.json").toFile(), root);
        }
    },
    SHOW(0) {
        @Override
        void action(List<String> args) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.readValue(Paths.get("notebook.json").toFile(), ObjectNode.class);

            String result = mapper.writeValueAsString(root);
            System.out.println(result);
        }
    },
    RM(1) {
        @Override
        void action(List<String> args) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.readValue(Paths.get("notebook.json").toFile(), ObjectNode.class);

            root.remove(args.get(0));
            mapper.writeValue(Paths.get("notebook.json").toFile(), root);
        }
    };

    /**
     * THe number of arguments for the operation.
     */
    private final int valence;

    /**
     * Constructor to store operations with their valences.
     *
     * @param i the valence that is assigned to the enum value
     */
    Operation(int i) {
        this.valence = i;
    }

    /**
     * Getter for valences of operations.
     *
     * @return int
     */
    public int getValence() {
        return valence;
    }

    /**
     * Abstract method to do actions according to the Operation type.
     *
     * @param args List of args (number of args already correct)
     */
    abstract void action(List<String> args) throws IOException;
}
