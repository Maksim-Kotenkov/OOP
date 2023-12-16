package ru.nsu.kotenkov.notebook;


import static org.kohsuke.args4j.ExampleMode.ALL;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import ru.nsu.kotenkov.notebook.exceptions.WrongCommandException;


/**
 * Notebook class, that manage CL arguments and call operations.
 */
public class Notebook {
    @Option(name = "-add", usage = "Command to add an element: -add [key] [value]")
    private boolean addCommand;

    @Option(name = "-show", usage = "Command to show all: -show")
    private boolean ShowCommand;

    @Option(name = "-rm", usage = "Command to delete an element: -rm [key]")
    private boolean RmCommand;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    /**
     * Build the tool wia ./gradlew jar.                --> DOESN'T WORK!!!
     * Run via java -jar build/libs/Task_1_5_2-1.0.jar. --> DOESN'T WORK!!!
     * <p>
     * Run from IDEA with arguments.                    --> WORKS FINE!!!
     *
     * @param args command line args
     */
    public static void main(String[] args) throws IOException {
        new Notebook().doMain(args);
    }

    public void doMain(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        // if you have a wider console, you could increase the value;
        // here 80 is also the default
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);

        } catch(CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: java SampleMain"+parser.printExample(ALL));

            return;
        }

        Operation action = null;
        if (addCommand) {
            action = Operation.ADD;
        }

        if (ShowCommand) {
            action = Operation.SHOW;
        }

        if (RmCommand) {
            action = Operation.RM;
        }

        assert action != null;
        action.action(arguments);

        // access non-option arguments
        System.out.println("other arguments are:");
        for( String s : arguments )
            System.out.println(s);
    }

    /**
     * Method to perform actions we need.
     *
     * @param arg str argument
     * @return Operation object
     * @throws WrongCommandException if is not a known command
     */
    private static Operation action(String arg) throws WrongCommandException {
        return switch (arg) {
            case "-add" -> Operation.ADD;
            case "-rm" -> Operation.RM;
            default -> throw new WrongCommandException(arg);
        };
    }
}
