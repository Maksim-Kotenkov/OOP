package ru.nsu.kotenkov.notebook;

public enum Operation {
    ADD(2) {
        @Override
        void action(String[] args) {
            System.out.println("addddddd");
        }
    },
    RM(1) {
        @Override
        void action(String[] args) {
            System.out.println("RMRMRMRMMR");
        }
    };

    /**
     * THe number of arguments for the operation.
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
     * Getter for valences of operations.
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
     */
    abstract void action(String[] args);
}
