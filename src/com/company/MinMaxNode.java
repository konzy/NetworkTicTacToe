package com.company;

import java.util.ArrayList;


/**
 * Brian Konzman and Daniel Slone
 */
public class MinMaxNode {
    private Game gameBoard;
    private Move move;
    private int value = 0;
    private ArrayList<MinMaxNode> possibleMoves = new ArrayList<>();

    public MinMaxNode(Game gameBoard, Move move) {
        this.gameBoard = gameBoard;
        this.move = move;

        if (gameBoard.getCurrentPlayer() == Game.Player.HUMAN) {
            value = 1000;
        } else {
            value = -1000;
        }

        createTree(gameBoard);
    }

    public MinMaxNode(Game gameBoard) {
        this.gameBoard = gameBoard;
        createTree(gameBoard);
    }

    public void createTree(Game gameBoard) {
        //Tree creation
        if (gameBoard.getGameState() == Game.GameState.PLAY) {
            ArrayList<Move> moves = gameBoard.validMoves();

            for (Move move : moves) {
                Game tempGame = (Game)gameBoard.clone();
                tempGame.playMove(move);
                MinMaxNode node = new MinMaxNode(tempGame, move);
                possibleMoves.add(node);
            }
            if (gameBoard.getCurrentPlayer() == Game.Player.CPU) {
                for (MinMaxNode possibleMove : possibleMoves) {
                    value = Math.max(possibleMove.value - 1, value);
                }
            } else {
                for (MinMaxNode possibleMove : possibleMoves) {
                    value = Math.min(possibleMove.value + 1, value);
                }
            }
        } else { // WIN, LOSE, or DRAW -- we are at the leaf node
            if (gameBoard.getGameState() == Game.GameState.TIE) {
                value = 0;
            } else if (gameBoard.getGameState() == Game.GameState.WIN && gameBoard.getCurrentPlayer() == Game.Player.CPU) {
                value = -10;
            } else if (gameBoard.getGameState() == Game.GameState.WIN && gameBoard.getCurrentPlayer() == Game.Player.HUMAN) {
                value = 10;
            }
        }
    }

    public Move bestMove() {
        if (gameBoard.validMoves().size() == 9) {//if first move
            return gameBoard.getRandomValidMove();
        }

        Move result = null;
        int bestValue = -1000;
        for (MinMaxNode possibleMove : possibleMoves) {
            if (possibleMove.value > bestValue) {
                result = possibleMove.move;
                bestValue = possibleMove.value;
            }
        }
        return result;
    }
}
