package com.company;

import java.util.Random;


/**
 * Created by konzy on 2/21/2017.
 */
public class Game {

    private String[][] board = new String[3][3];
    private Player currentPlayer;
    private Random random;

    private static final String BLANK = " ";

    enum Player {
        HUMAN,
        CPU
    }

    enum GameState {
        PLAY,
        DRAW,
        WIN
    }

    public Game(Player currentPlayer) {
        random = new Random();
        initializeBoard();
    }

    public boolean playMove(int x, int y) {
        if (board[x][y].equals(BLANK)) {
            board[x][y] = "X";
            return true;
        }
        return false;
    }

    private void getRandomCurrentPlayer() {
        currentPlayer = Player.HUMAN;

        if (random.nextInt(2) == 0) {
            currentPlayer = Player.CPU;
        }

    }

    private void initializeBoard() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                board[x][y] = " ";
            }
        }
    }

    private GameState getGamestate() {
        if ((board[0] == board[1] && board[1] == board[2]) ||
                (board[3] == board[4] && board[4] == board[5]) ||
                (board[6] == board[7] && board[7] == board[8]) ||
                (board[0] == board[3] && board[3] == board[6]) ||
                (board[1] == board[4] && board[4] == board[7]) ||
                (board[2] == board[5] && board[5] == board[8]) ||
                (board[0] == board[4] && board[4] == board[8]) ||
                (board[2] == board[4] && board[4] == board[6])) {
            return GameState.WIN;
        } else if (isBoardFull()) {
            return GameState.DRAW;
        }
        return GameState.PLAY;
    }

    private boolean isBoardFull () {
        for (String[] rows : board) {
            for (String s : rows) {
                if (s.equals(BLANK)) {
                    return false;
                }
            }
        }
        return true;
    }


}
