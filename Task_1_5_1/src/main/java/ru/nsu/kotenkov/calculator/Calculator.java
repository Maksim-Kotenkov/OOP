package ru.nsu.kotenkov.calculator;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


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
    public double run(String prompt) throws WrongCommandException{
        Stack<Object> tokenStack = tokenize(prompt);

        Stack<Double> numStack = new Stack<>();

        while (!tokenStack.empty()) {
            Object stackObj = tokenStack.pop();

            if (stackObj.getClass() == Double.class) {
                numStack.add((Double) stackObj);
            } else {
                Operation currOperation = (Operation) stackObj;
                List<Double> args = new ArrayList<>();
                if (numStack.size() < currOperation.getValence()) {
                    throw new WrongPromptOrderException("Wrong operations order in a given prompt");
                }
                for (int i = 0; i < currOperation.getValence(); i++) {
                    args.add(numStack.pop());
                }

                numStack.add(currOperation.operationHandling(args));
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
    private Stack<Object> tokenize(String prompt) throws WrongCommandException{
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
                Operation command = evalOperation(evaledString);
                if (command != null) {
                    newStack.add(command);
                } else {
                    throw new WrongCommandException(evaledString);
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
        for (int ch = 0; ch < number.length(); ch++) {
            if (!(('0' <= number.charAt(ch) && number.charAt(ch) <= '9') || number.charAt(ch) == '.')) {
                if (!(ch == 0 && number.charAt(ch) == '-' && number.length() > 1)) {
                    return false;
                }
            }
        }

        return true;
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
            default -> null;
        };
    }
}
