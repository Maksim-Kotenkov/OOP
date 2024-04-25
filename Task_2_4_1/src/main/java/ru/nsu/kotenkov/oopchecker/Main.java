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
        if (args.length == 0) {
            System.err.println("NO DIRECTIVES GIVEN");
            return;
        }

        GroovyShell shell = new GroovyShell();

        String mode = args[0];
        GroovyCodeSource source = null;
        switch (mode) {
            case ("clone"):
                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
                        "oopchecker/groovyscripts/cloning.groovy"));
                shell.run(source, Collections.singletonList(""));
                break;
            case ("check"):
//                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
//                        "oopchecker/groovyscripts/checkGroupLab.groovy"));
//                HashMap allLabResults = (HashMap) shell.run(source, Collections.singletonList(""));
//
//                System.out.println(allLabResults);

//                ResultsTable resultsTable = new ResultsTable(allLabResults);
//                resultsTable.showUglyTable();
//                resultsTable.showPrettyPanels();

//                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
//                        "oopchecker/graphics/ResultsTable.groovy"));
//                shell.run(source, Collections.singletonList(""));

                source = new GroovyCodeSource(new File("./src/main/java/ru/nsu/kotenkov/" +
                        "oopchecker/graphics/formHTML.groovy"));
                shell.run(source, Collections.singletonList(""));
                break;
            }

    }
}
