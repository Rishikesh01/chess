package org.chess;

import org.chess.board.Board;
import org.chess.gui.ChessBoard;

/**
 * @author Rishikesh
 * @project ${PROJECT_NAME}
 */
public class Main {
    public static void main(String[] args) {
        Board board = Board.standardBoard();
        ChessBoard chessBoard = new ChessBoard();
    }
}