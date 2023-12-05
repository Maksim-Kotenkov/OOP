package ru.nsu.kotenkov.calculator;


import java.util.List;
import ru.nsu.kotenkov.calculator.exceptions.ArithmeticalException;


/**
 * Enum for operating with math operations.
 * With it, we can choose what operation to do with
 * arguments according to the Enum value.
 */
public enum Operation {
    /**
     * What is implemented:
     *  Sin, cos, pow, natural log and basic math operations.
     */
    SIN(1),
    COS(1),
    POW(2),
    LOG(1),
    PLUS(2),
    MINUS(2),
    MUL(2),
    DIV(2);

    /**
     * Valence field for taking numbers from the stack
     * and giving them to the operation handler.
     */
    private final int valence;

    /**
     * Constructor to store operations with their valences.
     *
     * @param i the valence that is assigned to the enum value
     */
    Operation(int i) {
        this.valence = i;
    }

    /**
     * Getter for valences of operation.
     *
     * @return int
     */
    public int getValence() {
        return valence;
    }

    /**
     * Performing the operation. It doesn't matter at this moment
     * how many arguments we need, we'll handle it in the calcOperation func.
     * BUT we need to give this number of arguments to the handler, this is why
     * we check valence in the solve-func and get the number we need from
     * the stack to this handler.
     *
     * @param args numbers we'll do operation with
     * @return double value of the result
     */
    public double operationHandling(List<Double> args) {
        return switch (this) {
            case PLUS -> calcPlus(args);
            case MINUS -> calcMinus(args);
            case MUL -> calcMul(args);
            case DIV -> calcDiv(args);
            case SIN -> calcSin(args);
            case COS -> calcCos(args);
            case POW -> calcPow(args);
            case LOG -> calcLog(args);
        };
    }

    /**
     * Handler for the '+' operation.
     *
     * @param args list of arguments, but we'll take only two
     * @return the result
     */
    private double calcPlus(List<Double> args) {
        return args.get(0) + args.get(1);
    }

    /**
     * Handler for the '-' operation.
     *
     * @param args list of arguments, but we'll take only two
     * @return the result
     */
    private double calcMinus(List<Double> args) {
        return args.get(0) - args.get(1);
    }

    /**
     * Handler for the '*' operation.
     *
     * @param args list of arguments, but we'll take only two
     * @return the result
     */
    private double calcMul(List<Double> args) {
        return args.get(0) * args.get(1);
    }

    /**
     * Handler for the '/' operation.
     *
     * @param args list of arguments, but we'll take only two
     * @return the result
     */
    private double calcDiv(List<Double> args) {
        if (args.get(1) == 0) {
            throw new ArithmeticalException("Dividing by zero");
        }
        return args.get(0) / args.get(1);
    }

    /**
     * Handler for the 'sin' operation.
     *
     * @param args list of arguments, but we'll take only one
     * @return the result
     */
    private double calcSin(List<Double> args) {
        return Math.sin(args.get(0));
    }

    /**
     * Handler for the 'cos' operation.
     *
     * @param args list of arguments, but we'll take only one
     * @return the result
     */
    private double calcCos(List<Double> args) {
        return Math.cos(args.get(0));
    }

    /**
     * Handler for the 'log' operation.
     *
     * @param args list of arguments, but we'll take only one
     * @return the result
     */
    private double calcLog(List<Double> args) {
        if (args.get(0) <= 0) {
            throw new ArithmeticalException("Log from negative");
        }
        return Math.log(args.get(0));
    }

    /**
     * Handler for the 'pow' operation.
     *
     * @param args list of arguments, but we'll take only two
     * @return the result
     */
    private double calcPow(List<Double> args) {
        return Math.pow(args.get(0), args.get(1));
    }
}
