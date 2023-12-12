package ru.nsu.kotenkov.calculator;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.nsu.kotenkov.calculator.exceptions.ArithmeticalException;
import ru.nsu.kotenkov.calculator.exceptions.WrongCommandException;
import ru.nsu.kotenkov.calculator.exceptions.WrongPromptOrderException;


/**
 * Calculator class, that calculate prompts in prefix form.
 */
public class Calculator {

    /**
     * Solver method, that calculate the result.
     * It works with tokenized input string.
     * The tokenized form is a stack of Doubles and Operations (enum).
     * Every number from the stack of tokens is moved to the numStack
     * and every operation-token pops the number of numbers it needs from the numStack.
     *
     * @param prompt the string from input
     * @return the result
     */
    public double run(String prompt) throws WrongCommandException {
        Stack<Object> tokenStack = tokenize(prompt);
        Stack<Double> numStack = new Stack<>();
        boolean unMinusSet = false;

        while (!tokenStack.empty()) {
            Object stackObj = tokenStack.pop();

            if (stackObj instanceof Double value) {
                numStack.add(value);
            } else {
                assert stackObj instanceof Operation;
                Operation currOperation = (Operation) stackObj;
                List<Double> args = new ArrayList<>();
                if (numStack.size() < currOperation.getValence()) {
                    throw new WrongPromptOrderException("Wrong operations order in a given prompt");
                }
                for (int i = 0; i < currOperation.getValence(); i++) {
                    args.add(numStack.pop());
                }

                try {
                    if (currOperation == Operation.UNMINUS) {
                        unMinusSet = true;
                        continue;
                    }
                    if (unMinusSet) {
                        numStack.add(-1 * currOperation.calc(args));
                        unMinusSet = false;
                    } else {
                        numStack.add(currOperation.calc(args));
                    }
                } catch (ArithmeticalException exception) {
                    System.out.println("Wrong arguments for an operation: "
                            + currOperation
                            + "\nArguments: "
                            + args);
                    throw exception;
                }
            }
        }

        return numStack.pop();
    }

    /**
     * The tokenizer method, that forms a stack of numbers and operations from the String.
     *
     * @param prompt the string to be tokenized
     * @return the stack of tokens
     */
    private Stack<Object> tokenize(String prompt) throws WrongCommandException {
        Stack<Object> newStack = new Stack<>();

        while (!prompt.isEmpty()) {
            while (!prompt.isEmpty() && prompt.charAt(0) == ' ') {
                prompt = prompt.substring(1);
            }

            StringBuilder evalAnything = new StringBuilder();

            while (!prompt.isEmpty() && prompt.charAt(0) != ' ') {
                evalAnything.append(prompt.charAt(0));
                prompt = prompt.substring(1);
            }

            String evaledString = evalAnything.toString();
            if (checkNumber(evaledString)) {
                newStack.add(Double.parseDouble(evaledString));
            } else {
                Operation command;
                if (evaledString.charAt(0) == '-' && evaledString.length() > 1) {
                    evaledString = evaledString.substring(1);
                    command = evalOperation(evaledString);
                    newStack.add(command);
                    newStack.add(Operation.UNMINUS);
                } else {
                    command = evalOperation(evaledString);
                    newStack.add(command);
                }
            }
        }

        return newStack;
    }

    /**
     * Checker for numbers.
     *
     * @param number str form of possibly a number
     * @return true/false
     */
    private boolean checkNumber(String number) {
        if (number.charAt(0) == '-') {
            if (number.length() == 1) {
                return false;
            } else {
                number = number.substring(1);
            }
        }
        Stream<Character> stringStream = number.codePoints()
                .mapToObj(c -> (char) c);

        List<Character> allowedChars = Arrays
                .asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.');
        Set<Character> allowedSet = new HashSet<>(allowedChars);

        Set<Boolean> result = stringStream
                .collect(Collectors.toSet())
                .stream()
                .map(allowedSet::contains)
                .collect(Collectors.toSet());

        return !result.contains(false);
    }

    /**
     * A method that morphs string-like form of an operation to Operation enum.
     *
     * @param command string form of an operation
     * @return enum Operation
     */
    private Operation evalOperation(String command) {
        return switch (command) {
            case "+" -> Operation.PLUS;
            case "-" -> Operation.MINUS;
            case "*" -> Operation.MUL;
            case "/" -> Operation.DIV;
            case "sin" -> Operation.SIN;
            case "cos" -> Operation.COS;
            case "pow" -> Operation.POW;
            case "log" -> Operation.LOG;
            default -> throw new WrongCommandException("Wrong operation");
        };
    }
}
