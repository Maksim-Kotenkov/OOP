package ru.nsu.kotenkov.notebook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Tester class.
 */
public class NotebookTest {
    @Test
    @DisplayName("Add test")
    public void addCheckMany() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-add", "cringe_again", "a_l0t"});

        List<Notion> actual = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        assertFalse(actual.isEmpty());
        assertEquals("cringecringe_again",
                actual.get(0).getLabel() + actual.get(1).getLabel());

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Show test")
    public void showCheck() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String time = format.format(new java.util.Date());
        app.doMain(new String[]{"-add", "something", "content with spaces"});
        app.doMain("-show".split(" "));

        String expected = "=== YOUR NOTEBOOK ===\n"
                + "---\n"
                + time
                + "\n"
                + "something:\n"
                + "    content with spaces\n"
                + "---\n";

        String actual = out.toString();
        assertEquals(expected, actual);

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Show with dates test")
    public void showWithDateCheck() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        String timeFrom = "18.04.1920 8:20:00";
        String timeTo = "18.04.1920 10:20:00";
        app.doMain(new String[]{"-add", "something", "some info"});
        app.doMain(new String[]{"-add", "something again", "some info"});
        app.doMain(new String[]{"-show", timeFrom, timeTo});

        String expected = "=== YOUR NOTEBOOK ===\n";

        String actual = out.toString();
        assertEquals(expected, actual);

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Show with dates test with output")
    public void showWithDateCheckResults() throws IOException,
            ParseException,
            InterruptedException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        @SuppressWarnings("all")
        String timeFrom = format.format(new java.util.Date());
        TimeUnit.SECONDS.sleep(2);
        @SuppressWarnings("all")
        String time = format.format(new java.util.Date());
        app.doMain(new String[]{"-add", "something that is outdated", "some info"});
        TimeUnit.SECONDS.sleep(5);
        String timeTo = format.format(new java.util.Date());
        app.doMain(new String[]{"-add", "something", "some info"});
        app.doMain(new String[]{"-show", timeFrom, timeTo});

        String expected = "=== YOUR NOTEBOOK ===\n"
                + "---\n"
                + time
                + "\n"
                + "something that is outdated:\n"
                + "    some info\n"
                + "---\n";

        String actual = out.toString();
        assertEquals(expected, actual);

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Show with dates and filter")
    public void filterCheck() throws IOException, ParseException, InterruptedException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        @SuppressWarnings("all")
        String timeTo = "20.04.2100 10:20:00";
        @SuppressWarnings("all")
        String timeFrom = format.format(new java.util.Date());
        TimeUnit.SECONDS.sleep(5);
        String time = format.format(new java.util.Date());
        app.doMain(new String[]{"-add", "something", "some info"});
        app.doMain(new String[]{"-add", "anything", "not really interesting"});
        app.doMain(new String[]{"-show", timeFrom, timeTo, "som"});

        String expected = "=== YOUR NOTEBOOK ===\n"
                + "---\n"
                + time
                + "\n"
                + "something:\n"
                + "    some info\n"
                + "---\n";

        String actual = out.toString();
        assertEquals(expected, actual);

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Rm test")
    public void rmCheck() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-rm", "cringe"});

        List<Notion> actual = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        assertTrue(actual.isEmpty());

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

    @Test
    @DisplayName("Rm test and then add")
    public void rmCheckAndAdd() throws IOException, ParseException {
        File json = Paths.get("notebook.json").toFile();
        // save the previous version of notebook.json
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("all")
        List<Notion> backup = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        // rewrite notebook.json
        List<Notion> empty = new ArrayList<>();
        mapper.writerWithDefaultPrettyPrinter().writeValue(json, empty);

        Notebook app = new Notebook();

        app.doMain(new String[]{"-add", "cringe", "a_l0t"});
        app.doMain(new String[]{"-rm", "cringe"});
        app.doMain(new String[]{"-add", "new_cringe", "moooooore"});

        List<Notion> actual = new ArrayList<>(Arrays.asList(
                mapper.readValue(json, Notion[].class)));
        assertFalse(actual.isEmpty());
        assertEquals("new_cringe", actual.get(0).getLabel());

        mapper.writerWithDefaultPrettyPrinter().writeValue(json, backup);
    }

}
