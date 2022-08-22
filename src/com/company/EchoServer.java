package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class EchoServer extends Thread {
    private final int port;

    EchoServer(int port) {
        this.port = port;
    }

    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    public void run() {
        System.out.println("Connecting...");
        try (ServerSocket ss = new ServerSocket(port)) {
            //Обработка подключения
            try (Socket clientSocket = ss.accept()) {
                handle(clientSocket);
            }
        } catch (IOException e) {
            System.out.printf("Вероятнее всего порт %s занят %n", port);
            e.printStackTrace();
        }
    }

    private void handle(Socket clientSocket) throws IOException {
        //Логика обработки
        InputStream input = clientSocket.getInputStream();
        InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);
        //Scanner scn = new Scanner(isr);
        try (Scanner scn = new Scanner(isr)) {
            while (true) {
                String msg = scn.nextLine().strip();
                System.out.printf("Got: %s%n", msg);
                if ("end".equalsIgnoreCase(msg)) {
                    System.out.printf("Bye bye!%n");
                    return;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Client dropped the connection!");
        }
    }
}
