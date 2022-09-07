package org.chess.board;

import org.chess.piece.Piece;

/**
 * @author Rishikesh
 * @project chess
 */
public class Move {

    final Board board;
    final Piece piece;
    final int destCoordinate;

    public Move(Board board, Piece piece, int destCoordinate) {
        this.board = board;
        this.piece = piece;
        this.destCoordinate = destCoordinate;
    }

    public static class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece pieceMoved,
                         final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }
    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(Board board, Piece piece, int destCoordinate, Piece attackedPiece) {
            super(board, piece, destCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
