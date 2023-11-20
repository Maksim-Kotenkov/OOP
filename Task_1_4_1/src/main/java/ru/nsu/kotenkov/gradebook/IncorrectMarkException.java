package ru.nsu.kotenkov.gradebook;


/**
 * Custom exception class for value-out-of-bounds.
 */
public class IncorrectMarkException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message we print
     */
    public IncorrectMarkException(String errorMessage) {
        super(errorMessage);
    }
}
