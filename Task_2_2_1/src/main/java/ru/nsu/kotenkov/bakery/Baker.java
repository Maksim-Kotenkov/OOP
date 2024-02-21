package ru.nsu.kotenkov.bakery;

public class Baker {
    private int efficiency;
    private boolean workingState;

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public void setWorkingState(boolean workingState) {
        this.workingState = workingState;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public boolean isWorkingState() {
        return workingState;
    }
}
