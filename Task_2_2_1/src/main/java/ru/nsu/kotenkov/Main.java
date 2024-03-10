package ru.nsu.kotenkov;


import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.kotenkov.bakery.Bakery;
import ru.nsu.kotenkov.bakery.staff.Order;
import ru.nsu.kotenkov.bakery.staff.configuring.OrdersConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Bakery bakery = new Bakery(OrdersConfig.getOrders());

        bakery.start();
        try {
            bakery.join();
        } catch (InterruptedException e) {
            System.err.println("Bakery thread interrupted");
        }
    }
}
