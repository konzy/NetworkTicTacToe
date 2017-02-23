package com.company;

import java.util.Random;


/**
 * Created by konzy on 2/21/2017.
 */
public class Game implements Cloneable {

    private static final String BLANK = " ";
    private static final String X_LETTER = "X";
    private static final String O_LETTER = "O";
    private String[][] board = new String[3][3];
    private Player currentPlayer;
    private String currentSymbol = X_LETTER;
    private Random random;

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
        getRandomCurrentPlayer();
    }

    @Override
    public Object clone() {
        try {
            Game clone = (Game)super.clone();
            clone.board = board.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean playMove(int x, int y) {
        if (board[x][y].equals(BLANK)) {
            board[x][y] = currentSymbol;
            switchCurrentPlayer();
            return true;
        }
        return false;
    }

    private void switchCurrentPlayer() {
        if (currentPlayer == Player.HUMAN) {
            currentPlayer = Player.CPU;
        } else {
            currentPlayer = Player.HUMAN;
        }

        if (currentSymbol.equals(X_LETTER)) {
            currentSymbol = O_LETTER;
        } else {
            currentSymbol = X_LETTER;
        }
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
                board[x][y] = BLANK;
            }
        }
    }

    private GameState getGamestate() {
        if (isWin()) {
            return GameState.WIN;
        } else if (isBoardFull()) {
            return GameState.DRAW;
        }
        return GameState.PLAY;
    }

    private boolean isWin() {
        if ((board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2]) && !board[0][0].equals(BLANK)) ||
                (board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0]) && !board[0][2].equals(BLANK))) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            if ((board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i]) && !board[0][i].equals(BLANK)) ||
                    (board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2]) && !board[i][0].equals(BLANK))){
                return true;
            }
        }
        return false;
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
