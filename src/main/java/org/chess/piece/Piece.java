package org.chess.piece;

import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.Move;

import java.util.Collection;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Piece {
    protected final int piecePosition;
    protected final Color pieceColor;

    protected final boolean isFirstMove;
    protected final PieceType pieceType;

    private final int cachedHashCode;

    Piece(final int piecePosition, final Color pieceColor, final PieceType pieceType, final boolean isFirstMove) {
        this.pieceColor = pieceColor;
        this.piecePosition = piecePosition;
        this.pieceType = pieceType;
        this.isFirstMove = isFirstMove;

        cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int hash = pieceType.hashCode();
        hash = 31 * hash + pieceColor.hashCode();
        hash = 31 * hash + piecePosition;
        hash = 31 * hash + (isFirstMove ? 1 : 0);
        return hash;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Color getPieceColor() {
        return this.pieceColor;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public int getPiecePosition() {
        return this.piecePosition;
    }

    public abstract Piece movePiece(Move move);

    public PieceType getPieceType() {
        return this.pieceType;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Piece)) {
            return false;
        }
        final Piece somePiece = (Piece) obj;
        return piecePosition == somePiece.getPiecePosition() && pieceColor == somePiece.getPieceColor() &&
                pieceType == somePiece.getPieceType() && isFirstMove == somePiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }
}
