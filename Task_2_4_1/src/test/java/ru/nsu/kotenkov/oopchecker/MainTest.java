package ru.nsu.kotenkov.oopchecker;


import java.io.IOException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class MainTest {
    @Test
    public void checkMain() throws IOException {
        Main.main(new String[] {"check"});
        Assertions.assertTrue(true);
    }
}
