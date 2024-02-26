package ru.nsu.kotenkov.bakery.exceptions;


public class CourierInterrupted extends RuntimeException {
    /**
     * Constructor.
     *
     * @param errorMessage the message of the exception
     */
    public CourierInterrupted(String errorMessage) {
        super(errorMessage);
    }
}
