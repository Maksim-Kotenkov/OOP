package ru.nsu.kotenkov.primes.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import ru.nsu.kotenkov.primes.calculus.LinearChecker;


/**
 * A class for server, that distributes the work.
 */
public class Server {
    private final ServerSocket serverSocket;
    private final Socket[] clientSocket;
    private final PrintWriter[] out;
    private final BufferedReader[] in;
    private final int numOfClients;
    private final int[] testDataset;

    /**
     * In constructor, we get connections from clients, init our target array of numbers.
     *
     * @param port to host on
     * @param numOfClients how many machines we are waiting for
     * @param size of the target array of numbers
     * @throws IOException if ruined
     */
    public Server(int port, int numOfClients, int size) throws IOException {
        clientSocket = new Socket[numOfClients];
        out = new PrintWriter[numOfClients];
        in = new BufferedReader[numOfClients];
        this.numOfClients = numOfClients;

        this.testDataset = new int[size];
        Arrays.fill(testDataset, 2004991);  // fill with this prime number
        testDataset[size - 1] = 700;

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

    /**
     * Sending parts of the array and receiving results.
     * If a client has died, we get its part and perform it by ourselves.
     *
     * @return overall result for the whole array of numbers
     * @throws IOException if ruined
     */
    public boolean start() throws IOException {
        int batchSize = Math.floorDiv(this.testDataset.length, numOfClients + 1) + 1;
        for (int i = 0; i < numOfClients; i++) {
            out[i].println(Arrays.toString(
                            Arrays.copyOfRange(this.testDataset,
                                    i * batchSize,
                                    (i + 1) * batchSize)
                    )
            );
        }

        // our part should be there but for tests I moved it
        // this is to test broken connections

        for (int i = 0; i < numOfClients; i++) {
            try {
                String inRes = in[i].readLine();

                // means that clientSocket.getOutpurStream() now is null so client has problems
                if (inRes == null) {
                    throw new SocketException("Empty res from client");
                }

                boolean res = Boolean.parseBoolean(inRes);

                System.out.println("+ result: " + res);
                if (res) {
                    stop();
                    return true;
                }
            } catch (SocketException|SocketTimeoutException e) {
                // here we need to check it by ourselves
                boolean res = LinearChecker.check(
                        Arrays.copyOfRange(
                                this.testDataset,
                                i * batchSize,
                                (i + 1) * batchSize)
                );
                System.out.println("Socket exception, doing " + i
                        + " part by myself with the result: " + res);
                if (res) {
                    stop();
                    return true;
                }
            }
        }

        // and our part is the last (test ver)
        boolean myRes = LinearChecker.check(
                Arrays.copyOfRange(
                        this.testDataset,
                        numOfClients * batchSize,
                        this.testDataset.length)
        );

        if (myRes) {
            stop();
            return true;
        }

        stop();
        return false;
    }

    /**
     * Closing every socket.
     *
     * @throws IOException if ruined
     */
    public void stop() throws IOException {
        for (int i = 0; i < numOfClients; i++) {
            in[i].close();
            out[i].close();
            clientSocket[i].close();
        }

        serverSocket.close();
    }
}