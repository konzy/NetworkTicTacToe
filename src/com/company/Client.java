package com.company;

import java.io.*;
import java.net.Socket;

/**
 * Created by konzy on 2/23/2017.
 */
public class Client {

    public static final int PORT = 7788;
    private static final String EXIT = "exit";
    private static final String VALID_INPUT = "[012] [012]|" + EXIT;
    private static final String WELCOME = "Welcome to the game of TicTacToe";

    public static void main(String[] args) {
        try {
            System.out.println(WELCOME);
            Socket ticTacToeSocket = new Socket("localhost", 7788);
            PrintWriter out = new PrintWriter(ticTacToeSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(ticTacToeSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String response = in.readLine();


            Game game;
            if (response.equals(Game.HUMAN_FIRST)) {
                game = new Game(Game.Player.HUMAN);
            } else {
                game = new Game(Game.Player.CPU);
            }

            String input;

            while ((input = stdIn.readLine()) != null) {
                if (input.equals(EXIT)) {
                    System.exit(0);
                } else if (input.matches(VALID_INPUT)) {
                    int x = Integer.valueOf(input.substring(0, 1));
                    int y = Integer.valueOf(input.substring(2, 3));
                    if (game.isValidMove(x, y)) {
                        out.println("MOVE " + input);
                        game.playMove(x, y);
                        response = in.readLine();
                        parseResponse(response, game);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Game.GameState parseResponse(String response, Game game) {
        if (response.length() < 9) {
            //normal move
            game.playMove(response.charAt(6), response.charAt(8));
            return Game.GameState.PLAY;
        } else {
            //handle move 0 0 win
            //handle move 0 0 tie
            //handle move * * loss
            //handle move * * tie
        }
        return Game.GameState.DRAW;
    }

}
