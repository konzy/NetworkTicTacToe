package com.company;

import java.util.ArrayList;
import java.util.Random;


/**
 * Brian Konzman and Daniel Slone
 */
public class Game implements Cloneable {

    enum Player {
        HUMAN,
        CPU
    }

    enum GameState {
        PLAY,
        TIE,
        WIN,
        LOSS
    }

    public static final String HUMAN_FIRST = "NONE";
    private static final String BLANK = " ";
    private static final String SERVER_LETTER = "X";
    private static final String CLIENT_LETTER = "O";

    private String[][] board = new String[3][3];
    private Player currentPlayer;
    private Random random;

    public Game(Player currentPlayer) {
        random = new Random();
        initializeBoard();
        this.currentPlayer = currentPlayer;
    }

    @Override
    public Object clone() {
        try {
            Game clone = (Game)super.clone();
            String[][] cloneBoard = new String[3][3];
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    cloneBoard[x][y] = board[x][y];
                }
            }
            clone.board = cloneBoard;
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean playMove(int x, int y) {
        if (isValidMove(x, y)) {
            board[x][y] = getCurrentSymbol();
            switchCurrentPlayer();
            return true;
        }
        return false;
    }

    public String getCurrentSymbol() {
        if (currentPlayer == Player.HUMAN) {
            return CLIENT_LETTER;
        }
        return SERVER_LETTER;
    }

    public boolean playMove(Move move) {
        return playMove(move.x, move.y);
    }

    public boolean isValidMove(int x, int y) {
        return board[x][y].equals(BLANK);
    }

    public boolean isValidMove(Move m) {
        return isValidMove(m.x, m.y);
    }

    public ArrayList<Move> validMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        if (isWin()) {
            return moves;
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Move move = new Move(x, y);
                if (isValidMove(move)) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    public Move getRandomValidMove() {
        ArrayList<Move> moves = validMoves();
        return moves.get(random.nextInt(moves.size()));
    }

    private void switchCurrentPlayer() {
        if (currentPlayer == Player.HUMAN) {
            currentPlayer = Player.CPU;
        } else {
            currentPlayer = Player.HUMAN;
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

    public GameState getGameState() {
        if (isWin()) {
            return GameState.WIN;
        } else if (isBoardFull()) {
            return GameState.TIE;
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

    @Override
    public String toString() {
        String s = "";
        String bar = "|";
        s += "  0 1 2 \n";
        s += "0" + bar + board[0][0] + bar + board[1][0] + bar + board[2][0] + bar + "\n";
        s += "1" + bar + board[0][1] + bar + board[1][1] + bar + board[2][1] + bar + "\n";
        s += "2" + bar + board[0][2] + bar + board[1][2] + bar + board[2][2] + bar + "\n";
        return s;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
