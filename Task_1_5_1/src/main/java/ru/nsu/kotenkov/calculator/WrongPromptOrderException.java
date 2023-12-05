package ru.nsu.kotenkov.calculator;


/**
 * Custom exception for wrong operation commands.
 *
 */
public class WrongPromptOrderException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message of the exception
     */
    public WrongPromptOrderException(String errorMessage) {
        super(errorMessage);
    }
}
