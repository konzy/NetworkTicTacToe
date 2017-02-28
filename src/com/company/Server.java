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

    public static final String CLIENT_WIN_MESSAGE = "MOVE 0 0 WIN";
    public static final String CLIENT_TIE_MESSAGE = "MOVE 0 0 TIE";
    public static final String SERVER_WIN_APPEND_MESSAGE = "LOSS";
    public static final String SERVER_TIE_APPEND_MESSAGE = "TIE";

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        Game game;
        ServerSocket serverSocket = new ServerSocket(PORT, 10);

        System.out.println("running.");
        while(true) {
            Socket socket = serverSocket.accept();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            if (random.nextInt(2) == 1) {
                game = new Game(Game.Player.HUMAN);
                out.println(Game.HUMAN_FIRST);
            } else {
                game = new Game(Game.Player.CPU);
            }

            while (game.getGameState() == Game.GameState.PLAY) {
                if (game.getCurrentPlayer() == Game.Player.HUMAN) {//human
                    String temp = in.readLine().substring(5, 8);

                    Move humanMove = new Move(temp);
                    game.playMove(humanMove);
                    if (game.getGameState() == Game.GameState.WIN) {
                        out.println(CLIENT_WIN_MESSAGE);
                    } else if (game.getGameState() == Game.GameState.TIE) {
                        out.println(CLIENT_TIE_MESSAGE);
                    }
                } else {//cpu
                    MinMaxNode minMaxAI = new MinMaxNode(game);
                    Move cpuMove = minMaxAI.bestMove();
                    game.playMove(cpuMove);
                    String moveString = "MOVE " + cpuMove.toString();
                    if (game.getGameState() == Game.GameState.WIN) {
                        out.println(moveString + " " + SERVER_WIN_APPEND_MESSAGE);
                    } else if (game.getGameState() == Game.GameState.TIE) {
                        out.println(moveString + " " + SERVER_TIE_APPEND_MESSAGE);
                    } else {
                        out.println(moveString);
                    }
                }
            }
        }
    }

}
