package ru.nsu.kotenkov.notebook;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.kohsuke.args4j.Argument;
import ru.nsu.kotenkov.notebook.exceptions.WrongCommandException;


/**
 * Notebook class, that manage CL arguments and call operations.
 */
public class Notebook {
    /**
     * Build the tool wia ./gradlew jar.
     * Run via java -jar build/libs/Task_1_5_2-1.0.jar.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("The call without arguments!!!");
            return;
        }

        Operation command;
        try {
            command = action(args[0]);
        } catch (WrongCommandException exception) {
            System.err.println("You gave the app a wrong command. Aborting...");
            return;
        }
        String[] commandArgs = new String[args.length - 1];
        System.arraycopy(args, 1, commandArgs, 0, args.length - 1);

        command.action(commandArgs);
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
