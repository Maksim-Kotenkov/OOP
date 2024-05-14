package ru.nsu.kotenkov.primes.net;


import ru.nsu.kotenkov.primes.calculus.LinearChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;


public class Server {
    private ServerSocket serverSocket;
    private final Socket[] clientSocket;
    private final PrintWriter[] out;
    private final BufferedReader[] in;
    private final int numOfClients;

    public Server(int port, int numOfClients) throws IOException {
        clientSocket = new Socket[numOfClients];
        out = new PrintWriter[numOfClients];
        in = new BufferedReader[numOfClients];
        this.numOfClients = numOfClients;

        System.out.println("Receiving connections");
        serverSocket = new ServerSocket(port);
        for (int i = 0; i < numOfClients; i++) {
            clientSocket[i] = serverSocket.accept();
            out[i] = new PrintWriter(clientSocket[i].getOutputStream(), true);
            in[i] = new BufferedReader(new InputStreamReader(clientSocket[i].getInputStream()));
            System.out.println("+ client");
        }
        System.out.println("All clients have connected successfully");

    }

    public boolean start(int[] numbers) throws IOException {
        int batchSize = Math.floorDiv(numbers.length, numOfClients + 1) + 1;
        for (int i = 0; i < numOfClients; i++) {
            out[i].println(Arrays.toString(
                            Arrays.copyOfRange(numbers,
                                    i * batchSize,
                                    (i + 1) * batchSize)
                    )
            );
        }

        // and our part is the last
        boolean myRes = LinearChecker.check(Arrays.copyOfRange(numbers, numOfClients * batchSize, numbers.length));

        System.out.println("All parts are sent, waiting for results");

        if (myRes) {
            stop();
            return true;
        }

        for (int i = 0; i < numOfClients; i++) {
            try {
                boolean res = Boolean.parseBoolean(in[i].readLine());
                System.out.println("+ result: " + res);
                if (res) {
                    stop();
                    return true;
                }
            } catch (SocketException|SocketTimeoutException e) {
                // here we need to check it by ourselves
                boolean res = LinearChecker.check(Arrays.copyOfRange(numbers, i * batchSize, (i + 1) * batchSize));
                System.out.println("+ result: " + res);
                if (res) {
                    stop();
                    return true;
                }
            }

        }

        stop();
        return false;
    }

    public void stop() throws IOException {
        for (int i = 0; i < numOfClients; i++) {
            in[i].close();
            out[i].close();
            clientSocket[i].close();
        }

        serverSocket.close();
    }
}
