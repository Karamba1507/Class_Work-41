package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoClient extends Thread {

    private final int port;
    private final String host;

    EchoClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public static EchoClient connectTo(int port) {
        String localhost = "127.0.0.1";
        return new EchoClient(port, localhost);
    }

    public void run() {
        System.out.printf("Напиши end чтобы выйти.%n");
        try (Socket socket = new Socket(host, port)) {
            Scanner scn = new Scanner(System.in, StandardCharsets.UTF_8);
//            OutputStream outputStream = socket.getOutputStream();
//            PrintWriter pw = new PrintWriter(outputStream);

            try (OutputStream outputStream = socket.getOutputStream();
                 PrintWriter pw = new PrintWriter(outputStream)) {
                while (true) {
                    String msg = scn.nextLine();
                    pw.write(msg);
                    pw.write(System.lineSeparator());

                    pw.flush();

                    if ("end".equalsIgnoreCase(msg)) {
                        return;
                    }
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection dropped");
        } catch (IOException e) {
            System.out.printf("Can't connect to %s:%s !%n", host, port);
        }
    }
}
