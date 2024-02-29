package ru.nsu.kotenkov;

import ru.nsu.kotenkov.bakery.Baker;
import ru.nsu.kotenkov.bakery.Bakery;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            Bakery bakery = new Bakery();
//            for (Baker baker : bakery.getBakers()) {
//                System.out.println(baker.getEfficiency());
//            }
        } catch (IOException exception) {
            System.err.println("IOExceptions occurred during the run");
        }

    }
}