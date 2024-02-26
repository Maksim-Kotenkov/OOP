package ru.nsu.kotenkov.bakery.exceptions;


public class BakerInterrupted extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message of the exception
     */
    public BakerInterrupted(String errorMessage) {
        super(errorMessage);
    }
}
