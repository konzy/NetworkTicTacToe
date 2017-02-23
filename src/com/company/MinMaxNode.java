package com.company;

import static com.company.Game.Player.HUMAN;

/**
 * Created by slonej3 on 2/23/17.
 */
public class MinMaxNode {
    private Game gameBoard;
    private MinMaxNode root;
    private int move;
    private int value = 0;

    public MinMaxNode(Game gameBoard, MinMaxNode root, int move) {
        this.gameBoard = gameBoard;
        this.root = root;
        this.move = move;

        if (gameBoard.getCurrentPlayer() == HUMAN) {
            value = 1000;
        } else {
            value = -1000;
        }

        createTree(gameBoard);
    }

    public MinMaxNode(Game gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void createTree(Game gameBoard) {

    }

    public int bestMove() {
        return -1;
    }


    /* ---- GETTERS & SETTERS ---- */

    public Game getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Game gameBoard) {
        this.gameBoard = gameBoard;
    }

    public MinMaxNode getRoot() {
        return root;
    }

    public void setRoot(MinMaxNode root) {
        this.root = root;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getValue() {
        return value;
    }

}
