package com.company;

import java.io.*;
import java.net.Socket;

/**
 * Created by konzy on 2/23/2017.
 */
public class Client {


    private static final String EXIT = "exit";
    private static final String VALID_INPUT = "[012] [012]|" + EXIT;
    private static final String WELCOME = "Welcome to the game of TicTacToe";
    private static final String IN_QUEUE = "You are in queue to play with the server";
    private static final String CONNECTED = "You are connected to the server";

    public static void main(String[] args) throws IOException {
        System.out.println(WELCOME);
        System.out.println(IN_QUEUE);
        Socket ticTacToeSocket = new Socket("127.0.0.1", Server.PORT);
        PrintWriter out = new PrintWriter(ticTacToeSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(ticTacToeSocket.getInputStream()));
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String response = in.readLine();

        System.out.println(CONNECTED);

        Game game;
        if (response.equals(Game.HUMAN_FIRST)) {
            game = new Game(Game.Player.HUMAN);
        } else {
            game = new Game(Game.Player.CPU);
            parseResponse(response, game);
        }

        System.out.println(game.toString());

        String input;

        while ((input = stdIn.readLine()) != null) {
            if (input.equals(EXIT)) {
                System.exit(0);
            } else if (input.matches(VALID_INPUT)) {
                Move move = new Move(input);
                if (game.isValidMove(move)) {
                    out.println("MOVE " + input);
                    game.playMove(move);
                    System.out.println(game.toString());
                    response = in.readLine();
                    parseResponse(response, game);
                    System.out.println(game.toString());
                }
            }
        }
    }

    private static Game.GameState parseResponse(String response, Game game) {
        String s = response.substring(5, 8);
        game.playMove(new Move(s));
        if (response.length() < 9) {
            return Game.GameState.PLAY;
        } else {
            String gameOverMessage = response.substring(9).trim().toLowerCase();
            System.out.println(game.toString());
            if (gameOverMessage.equals(Game.GameState.TIE.name().toLowerCase())) {
                System.out.println("Tie Game, Try again");
            } else if (gameOverMessage.equals(Game.GameState.WIN.name().toLowerCase())) {
                System.out.println("You Win!");
            } else if (gameOverMessage.equals(Game.GameState.LOSS.name().toLowerCase())) {

                System.out.println("You Lose, good day Sir, you get NOTHING!");
            }
            System.exit(0);
        }
        return Game.GameState.TIE;
    }
}
