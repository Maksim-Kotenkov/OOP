package ru.nsu.kotenkov.oopchecker;


import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import lombok.SneakyThrows;
import java.io.File;
import java.io.IOException;
import java.util.Collections;


public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("Hello world!");
//        Clone.cloneRepo("https://github.com/Maksim-Kotenkov/OOP", "22213", "Maksim-Kotenkov");
        if (args.length == 0) {
            System.err.println("NO DIRECTIVES GIVEN");
            return;
        }

//        Binding binding = new Binding("script", null);
//        binding.setVariable("foo", new Integer(2));
        GroovyShell shell = new GroovyShell();


        String mode = args[0];
        try {
            switch (mode) {
                case ("clone"):
                    System.out.println("Getting groovy code");
                    GroovyCodeSource source = new GroovyCodeSource(new File("./cloning.groovy"));
//                    shell.evaluate(source);
                    shell.run(source, Collections.singletonList(""));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}
