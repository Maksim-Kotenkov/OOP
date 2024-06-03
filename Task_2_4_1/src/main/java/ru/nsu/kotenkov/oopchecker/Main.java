package ru.nsu.kotenkov.oopchecker;


import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import java.util.Collections;
import java.util.Objects;
import lombok.SneakyThrows;


/**
 * Main class to start up the app.
 */
public class Main {
    /**
     * Based main.
     *
     * @param args cli arguments
     */
    @SneakyThrows
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("NO DIRECTIVES GIVEN");
            return;
        }

        GroovyShell shell = new GroovyShell();

        String mode = args[0];
        GroovyCodeSource source;
        switch (mode) {
            case ("clone"):
                source = new GroovyCodeSource(Objects.requireNonNull(
                        Main.class.getResource("/groovyscripts/cloning.groovy")
                ));
                shell.run(source, Collections.singletonList(""));
                break;
            case ("check"):
                source = new GroovyCodeSource(Objects.requireNonNull(
                        Main.class.getResource("/graphics/formHTML.groovy")
                ));
                shell.run(source, Collections.singletonList(""));
                break;
            default:
                break;
        }
    }
}
