package ru.nsu.kotenkov.calculator;


import java.util.Scanner;
import ru.nsu.kotenkov.calculator.exceptions.ArithmeticalException;
import ru.nsu.kotenkov.calculator.exceptions.WrongCommandException;
import ru.nsu.kotenkov.calculator.exceptions.WrongPromptOrderException;


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
                System.err.println("You gave the calculator a wrong prompt. Try again.");
            } catch (WrongPromptOrderException exception) {
                System.err.println("You gave the calculator a prompt with "
                        + "wrong order of operations. Try again.");
            } catch  (ArithmeticalException exception) {
                System.err.println("You gave the calculator a prompt with "
                        + "dividing by zero. Try again.");
            }
        }
    }
}
