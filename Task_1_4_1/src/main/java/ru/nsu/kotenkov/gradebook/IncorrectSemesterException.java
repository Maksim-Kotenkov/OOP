package ru.nsu.kotenkov.gradebook;


/**
 * Custom exception class for value-out-of-bounds.
 */
public class IncorrectSemesterException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message we print
     */
    public IncorrectSemesterException(String errorMessage) {
        super(errorMessage);
    }
}
