package ru.nsu.kotenkov.bakery;


public class Order {
    private int timeToCook;
    private int timeToDeliver;

    public void setTimeToCook(int timeToCook) {
        this.timeToCook = timeToCook;
    }

    public void setTimeToDeliver(int timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    public int getTimeToCook() {
        return timeToCook;
    }

    public int getTimeToDeliver() {
        return timeToDeliver;
    }
}
