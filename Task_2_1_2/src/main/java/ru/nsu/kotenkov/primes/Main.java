package ru.nsu.kotenkov.primes;


import ru.nsu.kotenkov.primes.net.Client;
import ru.nsu.kotenkov.primes.net.Server;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("MODE NOT SPECIFIED");
            return;
        }

        int size = 1000000;

        int[] testDataset = new int[size];
        Arrays.fill(testDataset, 2004991);  // fill with this prime number
        testDataset[size - 1] = 700;

        int port = 666;
        String mode = args[0];
        switch (mode) {
            case "server":
                Server serv = new Server(port, 1);
                boolean result = serv.start(testDataset);
            case "client":
                Client client = new Client(args[1], port);
                client.start();
        }
        System.out.println("Hello world!");
    }
}
