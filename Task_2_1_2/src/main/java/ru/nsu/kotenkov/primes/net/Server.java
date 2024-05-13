package ru.nsu.kotenkov.primes.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class Server {
    private ServerSocket serverSocket;
    private Socket[] clientSocket;
    private PrintWriter[] out;
    private BufferedReader[] in;
    private int numOfClients;

    public Server(int port, int numOfClients) {
        clientSocket = new Socket[numOfClients];
        out = new PrintWriter[numOfClients];
        in = new BufferedReader[numOfClients];
        this.numOfClients = numOfClients;

        try {
            serverSocket = new ServerSocket(port);
            for (int i = 0; i < numOfClients; i++) {
                clientSocket[i] = serverSocket.accept();
                out[i] = new PrintWriter(clientSocket[i].getOutputStream(), true);
                in[i] = new BufferedReader(new InputStreamReader(clientSocket[i].getInputStream()));
                System.out.println("+ client");
            }
            System.out.println("All clients have connected successfully");
        } catch (IOException e) {
            System.err.println("ERR IN INIT SERVER: " + e);
        }

    }

    public boolean start(int[] numbers) {
        int batchSize = Math.floorDiv(numbers.length, numOfClients) + 1;
        for (int i = 0; i < numOfClients - 1; i++) {
            out[i].println(Arrays.toString(
                    Arrays.copyOfRange(numbers,
                            i * batchSize,
                            (i + 1) * batchSize)
                    )
            );
        }
        out[numOfClients - 1].println(Arrays.toString(
                        Arrays.copyOfRange(numbers,
                                (numOfClients - 2) * batchSize,
                                numbers.length)
                )
        );

        try {
            for (int i = 0; i < numOfClients; i++) {
                boolean res = Boolean.parseBoolean(in[i].readLine());
                if (res) {
                    stop();
                    return true;
                }
            }

            stop();
            return false;
        } catch (IOException e) {
            System.err.println("ERR WHILE WAITING FOR THE RES: " + e);
        }

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
