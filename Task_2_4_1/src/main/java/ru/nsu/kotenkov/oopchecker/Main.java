package ru.nsu.kotenkov.oopchecker;


import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import lombok.SneakyThrows;


/**
 * Main class to start up the app.
 */
public class Main {
    /**
     * Based main.
     *
     * @param args cli arguments
     * @throws IOException shell can be interrupted
     */
    @SneakyThrows
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("NO DIRECTIVES GIVEN");
            return;
        }

        GroovyShell shell = new GroovyShell();

        String mode = args[0];
        GroovyCodeSource source;
        switch (mode) {
            case ("clone"):
                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/"
                        + "oopchecker/groovyscripts/cloning.groovy"));
                shell.run(source, Collections.singletonList(""));
                break;
            case ("check"):
                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/"
                        + "oopchecker/graphics/formHTML.groovy"));
                shell.run(source, Collections.singletonList(""));
                break;
            default:
                break;
        }
    }
}
