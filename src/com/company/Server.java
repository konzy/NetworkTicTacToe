package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Brian Konzman and Daniel Slone
 */
public class Server {

    public static final int PORT = 7788;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT, 10);

        System.out.println("running.");
        while (true) {
            new MultiServerThread(serverSocket.accept()).start();
        }
    }
}
