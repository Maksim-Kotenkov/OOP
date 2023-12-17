package ru.nsu.kotenkov.notebook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester class.
 */
public class NotebookTest {

    @Test
    @DisplayName("Show test")
    public void showCheck() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        List<Notion> backup = new ArrayList<>(Arrays.asList(mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        app.doMain(new String[]{"-add", "something", "content with spaces"});
        app.doMain("-show".split(" "));

        assertTrue(true);

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Add test")
    public void addCheckMany() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        List<Notion> backup = new ArrayList<>(Arrays.asList(mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-add", "cringe_again", "a_l0t"});

        List<Notion> actual = new ArrayList<>(Arrays.asList(mapper.readValue(json, Notion[].class)));
        assertEquals("cringecringe_again",
                actual.get(0).getLabel() + actual.get(1).getLabel());

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Rm test")
    public void rmCheck() throws IOException, ParseException {
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
        assertEquals("[]", mapper.writeValueAsString(root));

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Rm test and then add")
    public void rmCheckAndAdd() throws IOException, ParseException {
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

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

}
