package ru.nsu.kotenkov.primes.net;


import ru.nsu.kotenkov.primes.calculus.LinearChecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;


public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IOERR IN CLIENT: " + e);
        }
    }

    public boolean start() {
        try {
            String myPart = in.readLine();
            int[] myPartInt = Arrays.stream(myPart.substring(1, myPart.length() - 1).split(", "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            System.out.println(myPartInt.length);

            boolean myRes = LinearChecker.check(myPartInt);

            System.out.println("My result: " + myRes);
            out.println(myRes);

            System.out.println("Result sent to the server");
            stop();
            return myRes;
        } catch (IOException e) {
            System.err.println("IOERR IN CLIENT WHILE PROCESSING ITS PART: " + e);
        }

        return false;
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
