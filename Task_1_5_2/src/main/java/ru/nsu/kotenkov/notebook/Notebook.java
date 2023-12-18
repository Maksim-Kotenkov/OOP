package ru.nsu.kotenkov.notebook;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


/**
 * Notebook class, that manage CL arguments and call operations.
 */
public class Notebook {
    /**
     * Optional arguments for operations with the notebook.
     */
    @Option(name = "-add", usage = "Command to add an element: -add [key] [value]")
    private boolean addCommand;

    @Option(name = "-show", usage = "Command to show all: -show")
    private boolean showCommand;

    @Option(name = "-rm", usage = "Command to delete an element: -rm [key]")
    private boolean rmCommand;

    @Argument
    private List<String> arguments = new ArrayList<>();

    /**
     * Build the tool wia ./gradlew jar.
     * Run via java -jar ./build/libs/Notebook-1.0.jar --> WORKS FINE!!!
     * or
     * Run from IDEA with arguments.                    --> WORKS FINE!!!
     *
     * @param args command line args
     * @throws IOException for reading/writing into the file
     * @throws ParseException exception for args4j
     */
    public static void main(String[] args) throws IOException, ParseException {
        new Notebook().doMain(args);
    }

    /**
     * Kinda another main.
     *
     * @param args command line args
     * @throws IOException for reading/writing into the file
     * @throws ParseException exception for args4j
     */
    public void doMain(String[] args) throws IOException, ParseException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

        } catch(CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        Operation action = null;
        if (addCommand) {
            addCommand = false;
            action = Operation.ADD;
        }

        if (showCommand) {
            showCommand = false;
            action = Operation.SHOW;
        }

        if (rmCommand) {
            rmCommand = false;
            action = Operation.RM;
        }

        assert action != null;
        action.action(arguments);
        arguments.clear();
    }
}
