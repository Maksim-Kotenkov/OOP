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

        int port = 666;
        String mode = args[0];
        try {
            switch (mode) {
                case "server":
                    Server serv = new Server(
                            port,
                            Integer.parseInt(args[1]),
                            Integer.parseInt(args[2])
                    );
                    boolean result = serv.start();
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
