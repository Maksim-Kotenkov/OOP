package ru.nsu.kotenkov.bakery.staff;


public interface Staff {
    boolean isReady();

    void setReady(boolean ready);

    void setOrder(Order order);

    void run();
}
