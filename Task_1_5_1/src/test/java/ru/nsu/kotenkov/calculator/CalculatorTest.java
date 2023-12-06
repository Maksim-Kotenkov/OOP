package ru.nsu.kotenkov.calculator;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.kotenkov.calculator.exceptions.ArithmeticalException;
import ru.nsu.kotenkov.calculator.exceptions.WrongCommandException;
import ru.nsu.kotenkov.calculator.exceptions.WrongPromptOrderException;


/**
 * Empty yet.
 */
public class CalculatorTest {
    @Test
    @DisplayName("Operation class valence check")
    void checkOperation() {
        Operation op = Operation.LOG;

        assertEquals(1, op.getValence());
    }

    @Test
    @DisplayName("Operation class valence check")
    void checkOperation2() {
        Operation op = Operation.PLUS;

        assertEquals(2, op.getValence());
    }

    @Test
    @DisplayName("Test the number")
    void checkNumber() {
        Calculator app = new Calculator();
        double result = app.run("22213");

        assertEquals(22213, result);
    }

    @Test
    @DisplayName("Test a simple sum prompt")
    void checkSumSimple() {
        Calculator app = new Calculator();
        double result = app.run("+ 1 999");

        assertEquals(1000, result);
    }

    @Test
    @DisplayName("Test sum with negatives")
    void checkSumWithNegative() {
        Calculator app = new Calculator();
        double result = app.run("+ 1 -1");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Test subtraction operation")
    void checkSub() {
        Calculator app = new Calculator();
        double result = app.run("- 1 999");

        assertEquals(-998, result);
    }

    @Test
    @DisplayName("Test subtraction with negative number")
    void checkSubWithNegative() {
        Calculator app = new Calculator();
        double result = app.run("- 1 -1");

        assertEquals(2, result);
    }

    @Test
    @DisplayName("Test multiplication")
    void checkMul() {
        Calculator app = new Calculator();
        double result = app.run("* 3 12");

        assertEquals(36, result);
    }

    @Test
    @DisplayName("Test multiplication with negative")
    void checkMulNegative() {
        Calculator app = new Calculator();
        double result = app.run("* -3 12");

        assertEquals(-36, result);
    }

    @Test
    @DisplayName("Test multiplication with both negative")
    void checkMulBothNegative() {
        Calculator app = new Calculator();
        double result = app.run("* -3 -12");

        assertEquals(36, result);
    }

    @Test
    @DisplayName("Test division")
    void checkDiv() {
        Calculator app = new Calculator();
        double result = app.run("/ 3 12");

        assertEquals(0.25, result);
    }

    @Test
    @DisplayName("Test division with negative")
    void checkDivNegative() {
        Calculator app = new Calculator();
        double result = app.run("/ -3 12");

        assertEquals(-0.25, result);
    }

    @Test
    @DisplayName("Test division with both negative")
    void checkDivBothNegative() {
        Calculator app = new Calculator();
        double result = app.run("/ -3 -12");

        assertEquals(0.25, result);
    }

    @Test
    @DisplayName("Test a simple pow")
    void checkPow() {
        Calculator app = new Calculator();
        double result = app.run("pow 1 10");

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Test a negative pow operation")
    void checkPowNegative() {
        Calculator app = new Calculator();
        double result = app.run("pow 2 -1");

        assertEquals(0.5, result);
    }

    @Test
    @DisplayName("Test a negative number in power")
    void checkPowNegativeToPositive() {
        Calculator app = new Calculator();
        double result = app.run("pow -2 4");

        assertEquals(16, result);
    }

    @Test
    @DisplayName("Test a negative number in power with unary minus")
    void checkPowNegativeToPositiveUnary() {
        Calculator app = new Calculator();
        double result = app.run("-pow -2 4");

        assertEquals(-16, result);
    }

    @Test
    @DisplayName("Test a sin operation")
    void checkSin() {
        Calculator app = new Calculator();
        double result = app.run("sin + 1 -1");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Test a cos operation")
    void checkCos() {
        Calculator app = new Calculator();
        double result = app.run("cos + 1 -1");

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Test a cos with unary minus")
    void checkCosUnary() {
        Calculator app = new Calculator();
        double result = app.run("-cos + 1 -1");

        assertEquals(-1, result);
    }

    @Test
    @DisplayName("Test a log operation")
    void checkLn() {
        Calculator app = new Calculator();
        double result = app.run("log + 1 0");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Test all in one")
    void checkEverything() {
        Calculator app = new Calculator();
        double result = app.run("pow / + sin 0 * 1 8 - cos 0 -3 10");

        assertEquals(1024, result);
    }

    @Test
    @DisplayName("Test all in one with doubles")
    void checkEverythingWithDouble() {
        Calculator app = new Calculator();
        double result = app.run("pow 2.7182818284 log pow / + sin 0 * 1 8 - cos 0 -3 10");

        assertEquals(1024, result, 10E-7);
    }

    @Test
    @DisplayName("Check wrong command exception")
    void checkException() {
        assertThrowsExactly(WrongCommandException.class, () -> {
            Calculator app = new Calculator();
            app.run("sinn 0");
        });
    }

    @Test
    @DisplayName("Check double '-' wrong command exception")
    void checkException2() {
        assertThrowsExactly(WrongCommandException.class, () -> {
            Calculator app = new Calculator();
            app.run("+ 0 --2");
        });
    }

    @Test
    @DisplayName("Check just the '-' wrong command exception")
    void checkExceptionOnlyMinus() {
        assertThrowsExactly(WrongPromptOrderException.class, () -> {
            Calculator app = new Calculator();
            app.run("+ 0 -");
        });
    }

    @Test
    @DisplayName("Check the wrong order of correct commands")
    void checkOrder() {
        assertThrowsExactly(WrongPromptOrderException.class, () -> {
            Calculator app = new Calculator();
            app.run("+ sin 0 + 1");
        });
    }

    @Test
    @DisplayName("Dividing by zero exception")
    void checkDividingByZero() {
        assertThrowsExactly(ArithmeticalException.class,
                () -> {
            Calculator app = new Calculator();
            app.run("/ 1000 0");
            },
                "Wrong arguments for an operation: DIV\n"
                        + "Arguments: [1000.0, 0.0]"
        );
    }

    @Test
    @DisplayName("Log from a negative number")
    void checkNegativeLog() {
        assertThrowsExactly(ArithmeticalException.class,
                () -> {
            Calculator app = new Calculator();
            app.run("log -2");
            },
                "Wrong arguments for an operation: LOG\n"
                        + "Arguments: [-2.0]"
        );
    }
}
