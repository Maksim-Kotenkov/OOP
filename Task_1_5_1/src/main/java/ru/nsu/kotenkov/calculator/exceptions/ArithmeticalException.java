package ru.nsu.kotenkov.calculator.exceptions;


/**
 * Custom exception for dividing by zero.
 *
 */
public class ArithmeticalException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message of the exception
     */
    public ArithmeticalException(String errorMessage) {
        super(errorMessage);
    }
}
