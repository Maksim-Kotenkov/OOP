package ru.nsu.kotenkov.primes.net;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Stream;
import ru.nsu.kotenkov.primes.calculus.LinearChecker;


/**
 * A class for client mode.
 */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Connecting to the server and setting shutdown hook.
     *
     * @param ip what machine to connect to
     * @param port what ports to connect
     */
    public Client(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Running Shutdown Hook");
                try {
                    Client.this.stop();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        } catch (IOException e) {
            System.err.println("IOERR IN CLIENT: " + e);
        }
    }

    /**
     * Receiving our part of work, performing and sending the res.
     * There is a timeout for testing the death of our machine with SIGINT.
     * Also, I tried to minimize memory allocation.
     */
    public void start() {
        //TODO catch interruptions from server
        try {
            while (true) {
                String myPart = in.readLine();
                String subS = myPart.substring(1, myPart.length() - 1);
                myPart = null;
                Stream<String> myPartStream = Arrays.stream(subS.split(", "));
                subS = null;
                int[] myPartInt = myPartStream
                        .mapToInt(Integer::parseInt)
                        .toArray();
                System.out.println(myPartInt.length);

                boolean myRes = LinearChecker.check(myPartInt);

                System.out.println("My result: " + myRes);

                // to check errors on the client side
                System.out.println("Timeout before sending: 5 sec");
                Thread.sleep(5000);
                out.println(myRes);

                System.out.println("Result sent to the server");
            }
        } catch (IOException e) {
            System.err.println("IOERR IN CLIENT WHILE PROCESSING ITS PART: " + e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Closing connection.
     *
     * @throws IOException if ruined
     */
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
