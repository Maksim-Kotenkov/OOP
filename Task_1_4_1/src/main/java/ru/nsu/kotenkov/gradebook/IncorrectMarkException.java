package ru.nsu.kotenkov.gradebook;

public class IncorrectMarkException extends RuntimeException {
    public IncorrectMarkException(String errorMessage) {
        super(errorMessage);
    }
}
