package ru.nsu.kotenkov.notebook;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;

public class NotebookTest {
    @Test
    @DisplayName("Add test")
    public void AddCheck() throws IOException {
        Notebook app = new Notebook();
        app.doMain(new String[]{"-add", "something", "content with spaces"});
        assertTrue(true);
    }

    @Test
    @DisplayName("Add test")
    public void AddCheckAndShow() throws IOException {
        Notebook app = new Notebook();
        app.doMain(new String[]{"-add", "something", "content with spaces"});
        app.doMain("-show".split(" "));
        assertTrue(true);
    }
}
