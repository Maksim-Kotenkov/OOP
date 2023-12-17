package ru.nsu.kotenkov.notebook;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


/**
 * Tester class.
 */
public class NotebookTest {

    @Test
    @DisplayName("Show test")
    public void ShowCheck() throws IOException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode backup = mapper.readValue(json, ObjectNode.class);
        // rewrite notebook.json
        ObjectNode root = mapper.createObjectNode();
        mapper.writeValue(json, root);

        Notebook app = new Notebook();

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        app.doMain(new String[]{"-add", "something", "content with spaces"});
        app.doMain("-show".split(" "));

        String actual = out.toString();
        assertEquals("{\"something\":\"content with spaces\"}\n", actual);

        mapper.writeValue(json, backup);
    }

    @Test
    @DisplayName("Add test")
    public void AddCheckMany() throws IOException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode backup = mapper.readValue(json, ObjectNode.class);
        // rewrite notebook.json
        ObjectNode root = mapper.createObjectNode();
        mapper.writeValue(json, root);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-add", "cringe_again", "a_l0t"});

        root = mapper.readValue(json, ObjectNode.class);
        assertEquals("{\"cringe\":\"a_l0t\","
                + "\"cringe_again\":\"a_l0t\"}", mapper.writeValueAsString(root));

        mapper.writeValue(json, backup);
    }

    @Test
    @DisplayName("Rm test")
    public void RmCheck() throws IOException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode backup = mapper.readValue(json, ObjectNode.class);
        // rewrite notebook.json
        ObjectNode root = mapper.createObjectNode();
        mapper.writeValue(json, root);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-rm", "cringe"});

        root = mapper.readValue(json, ObjectNode.class);
        assertEquals("{}", mapper.writeValueAsString(root));

        mapper.writeValue(json, backup);
    }

    @Test
    @DisplayName("Rm test and then add")
    public void RmCheckAndAdd() throws IOException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode backup = mapper.readValue(json, ObjectNode.class);
        // rewrite notebook.json
        ObjectNode root = mapper.createObjectNode();
        mapper.writeValue(json, root);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-rm", "cringe"});
        app.doMain(new String[]{"-add", "new_cringe", "moooooore"});

        root = mapper.readValue(json, ObjectNode.class);
        assertEquals("{\"new_cringe\":\"moooooore\"}", mapper.writeValueAsString(root));

        mapper.writeValue(json, backup);
    }

}
