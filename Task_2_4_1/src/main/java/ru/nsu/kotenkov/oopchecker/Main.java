package ru.nsu.kotenkov.oopchecker;


import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import lombok.SneakyThrows;
import java.io.File;
import java.io.IOException;
import java.util.Collections;


public class Main {
    @SneakyThrows
    public static void main(String[] args) throws IOException {
//        Clone.cloneRepo("https://github.com/Maksim-Kotenkov/OOP", "22213", "Maksim-Kotenkov");
        if (args.length == 0) {
            System.err.println("NO DIRECTIVES GIVEN");
            return;
        }

//        Binding binding = new Binding("script", null);
//        binding.setVariable("foo", new Integer(2));
        GroovyShell shell = new GroovyShell();


        String mode = args[0];
        GroovyCodeSource source = null;
        switch (mode) {
            case ("clone"):
                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/oopchecker/cloning.groovy"));
                shell.run(source, Collections.singletonList(""));
                break;
            case ("check"):
                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/oopchecker/checkGroupLab.groovy"));
                shell.run(source, Collections.singletonList(""));
//                    Testing test = new Testing();
//                    test.evaluate();
            }

    }
}
