package ru.nsu.kotenkov.calculator;

public class WrongCommandException extends RuntimeException{
    public WrongCommandException(String errorMessage) {
        super(errorMessage);
    }
}
