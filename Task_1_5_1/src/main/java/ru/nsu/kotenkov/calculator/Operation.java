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
    SIN(1) {
        /**
         * Handler for the 'sin' operation.
         *
         * @param args list of arguments, but we'll take only one
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            return Math.sin(args.get(0));
        }
    },
    COS(1) {
        /**
         * Handler for the 'cos' operation.
         *
         * @param args list of arguments, but we'll take only one
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            return Math.cos(args.get(0));
        }
    },
    POW(2) {
        /**
         * Handler for the 'pow' operation.
         *
         * @param args list of arguments, but we'll take only two
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            return Math.pow(args.get(0), args.get(1));
        }
    },
    LOG(1) {
        /**
         * Handler for the 'log' operation.
         *
         * @param args list of arguments, but we'll take only one
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            if (args.get(0) <= 0) {
                throw new ArithmeticalException("Log from negative");
            }
            return Math.log(args.get(0));
        }
    },
    PLUS(2) {
        /**
         * Handler for the '+' operation.
         *
         * @param args list of arguments, but we'll take only two
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            return args.get(0) + args.get(1);
        }
    },
    MINUS(2) {
        /**
         * Handler for the '-' operation.
         *
         * @param args list of arguments, but we'll take only two
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            return args.get(0) - args.get(1);
        }
    },
    MUL(2) {
        /**
         * Handler for the '*' operation.
         *
         * @param args list of arguments, but we'll take only two
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            return args.get(0) * args.get(1);
        }
    },
    DIV(2) {
        /**
         * Handler for the '/' operation.
         *
         * @param args list of arguments, but we'll take only two
         * @return the result
         */
        @Override
        protected double calc(List<Double> args) {
            if (args.get(1) == 0) {
                throw new ArithmeticalException("Dividing by zero");
            }
            return args.get(0) / args.get(1);
        }
    },
    UNMINUS(0) {
        /**
         * Just a something to return...
         * @param args okay args... but empty
         * @return -1
         */
        @Override
        protected double calc(List<Double> args) {
            return -1;
        }
    };

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
     * Abstract method to do actions according to the Operation type.
     *
     * @param args List of args (number of args already correct)
     * @return the result of the Operation
     */
    abstract double calc(List<Double> args);
}
