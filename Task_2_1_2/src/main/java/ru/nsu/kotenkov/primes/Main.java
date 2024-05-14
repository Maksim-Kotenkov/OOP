package ru.nsu.kotenkov.primes;


import ru.nsu.kotenkov.primes.net.Client;
import ru.nsu.kotenkov.primes.net.Server;

import java.io.IOException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("MODE NOT SPECIFIED");
            return;
        }

        int size = 100000000;

        int[] testDataset = new int[size];
        Arrays.fill(testDataset, 2004991);  // fill with this prime number
        testDataset[size - 1] = 700;

        int port = 666;
        String mode = args[0];
        try {
            switch (mode) {
                case "server":
                    Server serv = new Server(port, Integer.parseInt(args[1]));
                    boolean result = serv.start(testDataset);
                    System.out.println("OVERALL RESULT: " + result);
                    return;
                case "client":
                    Client client = new Client(args[1], port);
                    client.start();
            }
        } catch (IOException e) {
            System.err.println("IOERR: " + e);
        }

    }
}
