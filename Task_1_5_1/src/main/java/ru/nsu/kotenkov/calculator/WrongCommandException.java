package ru.nsu.kotenkov.calculator;


/**
 * Custom exception for wrong operation commands.
 *
 */
public class WrongCommandException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message of the exception
     */
    public WrongCommandException(String errorMessage) {
        super(errorMessage);
    }
}
