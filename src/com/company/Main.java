package com.company;



public class Main {

    public static void main(String[] args) {

        EchoServer server = new EchoServer(8788);
        server.start();

        EchoClient client = new EchoClient(8788, "127.0.0.1");
        client.start();
    }
}
