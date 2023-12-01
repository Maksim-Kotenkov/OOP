package ru.nsu.kotenkov.calculator;


import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;


public class Calculator {

    private Stack<Object> operationStack;
    public void run() {
        Scanner inpScan = new Scanner(System.in);
        String prompt = inpScan.nextLine();
        double result = this.solve(prompt);
        System.out.println("The result: " + result);
    }

    private double solve(String prompt) {
        this.operationStack = eval(prompt);

        System.out.println(operationStack);

        return 0;
    }

    private Stack<Object> eval(String prompt) {
        Stack<Object> newStack = new Stack<>();

        while (!prompt.isEmpty()) {
            while (!prompt.isEmpty() && prompt.charAt(0) == ' ') {
                prompt = prompt.substring(1);
            }

            StringBuilder evalAnything = new StringBuilder();
            while (!prompt.isEmpty() && '0' <= prompt.charAt(0) && prompt.charAt(0) <= '9') {
                evalAnything.append(prompt.charAt(0));
                prompt = prompt.substring(1);
            }
            if (!evalAnything.isEmpty()) {
                String possiblyNumber = evalAnything.toString();
                if (checkNumber(possiblyNumber)) {
                    newStack.add(Double.parseDouble(possiblyNumber));
                    continue;
                } else {
                    throw new WrongCommandException(possiblyNumber);
                }
            }

            while (!prompt.isEmpty() && prompt.charAt(0) != ' ') {
                evalAnything.append(prompt.charAt(0));
                prompt = prompt.substring(1);
            }

            String command = evalAnything.toString();
            if (checkCommand(command)) {
                newStack.add(command);
            } else {
                throw new WrongCommandException(command);
            }
        }

        return newStack;
    }

    private boolean checkNumber(String number) {
        for (int ch = 0; ch < number.length(); ch++) {
            if (!('0' <= number.charAt(ch) && number.charAt(ch) <= '9')) {
                return false;
            }
        }

        return true;
    }

    private boolean checkCommand(String command) {
        return command.equals("sin")
                || command.equals("+")
                || command.equals("-")
                || command.equals("*")
                || command.equals("/");
    }
}
