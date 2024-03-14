package ru.nsu.kotenkov;


import ru.nsu.kotenkov.bakery.Bakery;
import ru.nsu.kotenkov.bakery.staff.configuring.OrdersConfig;


/**
 * Just start the bakery, that's all.
 */
public class Main {
    /**
     * OMG main.
     *
     * @param args based
     */
    public static void main(String[] args) {
        Bakery bakery = new Bakery(OrdersConfig.getOrders());

        bakery.run();
    }
}
