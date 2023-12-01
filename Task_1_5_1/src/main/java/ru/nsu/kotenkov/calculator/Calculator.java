package ru.nsu.kotenkov.calculator;


import java.util.*;


public class Calculator {

    private enum Operation {
        SIN(1),
        COS(1),
        POW(2),
        PLUS(2),
        MINUS(2),
        MUL(2),
        DIV(2);

        private final int val;

        /**
         * Constructor to store operations with their valences.
         *
         * @param i the valence that is assigned to the enum value
         */
        Operation(int i) {
            this.val = i;
        }

        /**
         * Getter for valences of operation.
         *
         * @return int
         */
        public int getValence() {
            return val;
        }

        public double apply(List<Double> args) {
            return switch (this) {
                case PLUS -> calcPlus(args);
                case MINUS -> calcMinus(args);
                case MUL -> calcMul(args);
                case DIV -> calcDiv(args);
                default -> 0;
            };
        }

        private double calcPlus(List<Double> args) {
            return args.get(0) + args.get(1);
        }

        private double calcMinus(List<Double> args) {
            return args.get(0) - args.get(1);
        }

        private double calcMul(List<Double> args) {
            return args.get(0) * args.get(1);
        }

        private double calcDiv(List<Double> args) {
            return args.get(0) / args.get(1);
        }
    }

    private Stack<Object> operationStack;

    public void run() {
        Scanner inpScan = new Scanner(System.in);
        String prompt = inpScan.nextLine();
        double result = this.solve(prompt);
        System.out.println("The result: " + result);
    }

    private double solve(String prompt) {
        this.operationStack = eval(prompt);

//        System.out.println(operationStack);

        Stack<Double> numStack = new Stack<>();

        while (!operationStack.empty()) {
            Object stackObj = operationStack.pop();

            if (stackObj.getClass() == Double.class) {
                numStack.add((Double) stackObj);
            } else {
                Operation currOperation = (Operation) stackObj;
                List<Double> args = new ArrayList<>();
                for (int i = 0; i < currOperation.getValence(); i++) {
                    args.add(numStack.pop());
                }

                numStack.add(currOperation.apply(args));
            }
        }

        return numStack.pop();
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

            Operation command = evalOperation(evalAnything.toString());
            if (command != null) {
                newStack.add(command);
            } else {
                throw new WrongCommandException(evalAnything.toString());
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

    private Operation evalOperation(String command) {
        return switch (command) {
            case "+" -> Operation.PLUS;
            case "-" -> Operation.MINUS;
            case "*" -> Operation.MUL;
            case "/" -> Operation.DIV;
            case "sin" -> Operation.SIN;
            case "cos" -> Operation.COS;
            case "pow" -> Operation.POW;
            default -> null;
        };
    }
}
