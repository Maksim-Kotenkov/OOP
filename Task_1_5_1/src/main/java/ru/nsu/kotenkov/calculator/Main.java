package ru.nsu.kotenkov.calculator;


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
        Scanner inpScan = new Scanner(System.in);
        String prompt = inpScan.nextLine();

        Calculator app = new Calculator();
        double result = app.run(prompt);
        System.out.println("The result: " + result);
    }
}
