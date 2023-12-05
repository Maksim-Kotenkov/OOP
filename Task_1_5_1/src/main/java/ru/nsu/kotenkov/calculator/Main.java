package ru.nsu.kotenkov.calculator;


import ru.nsu.kotenkov.calculator.exceptions.ArithmeticalException;
import ru.nsu.kotenkov.calculator.exceptions.WrongCommandException;
import ru.nsu.kotenkov.calculator.exceptions.WrongPromptOrderException;

import java.util.Scanner;


/**
 * Main class that creates and starts the calculator app.
 */
public class Main {
    /**
     * Main:main.
     *
     * @param args args
     */
    public static void main(String[] args) {
        while (true) {
            try {
                Scanner inpScan = new Scanner(System.in);
                String prompt = inpScan.nextLine();

                Calculator app = new Calculator();
                double result = app.run(prompt);
                System.out.println("The result: " + result);
            } catch (WrongCommandException exception) {
                System.out.println("You gave the calculator a wrong prompt. Try again.");
            } catch (WrongPromptOrderException exception) {
                System.out.println("You gave the calculator a prompt with "
                        + "wrong order of operations. Try again.");
            } catch  (ArithmeticalException exception) {
                System.out.println("You gave the calculator a prompt with "
                        + "dividing by zero. Try again.");
            }
        }
    }
}
