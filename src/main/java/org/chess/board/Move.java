package org.chess.board;

import org.chess.piece.Pawn;
import org.chess.piece.Piece;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Move {

    public static final Move NULL_MOVE = new NullMove();
    final Board board;
    final Piece movedPiece;
    final int destCoordinate;

    public Move(Board board, Piece movedPiece, int destCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordinate = destCoordinate;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 99 * hash + this.destCoordinate;
        hash = 99 * hash + this.movedPiece.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Move)) {
            return false;
        }
        final Move someMove = (Move) obj;
        return getCurrentCoordinate() == someMove.getCurrentCoordinate() &&
                getDestinationCoordinate() == someMove.getDestinationCoordinate() &&
                getMovedPiece().equals(someMove.getMovedPiece());
    }

    public int getDestinationCoordinate() {
        return this.destCoordinate;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }


        for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        return builder.build();
    }

    private int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
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

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AttackMove))
                return false;
            final AttackMove attackMove = (AttackMove) obj;
            return super.equals(attackMove) && getAttackedPiece() == attackMove.getAttackedPiece();
        }
    }

    public static class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece pieceMoved,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, pieceMoved, destinationCoordinate, attackedPiece);
        }
    }

    public static class PawnPassEntAttackMove extends PawnAttackMove {

        public PawnPassEntAttackMove(Board board, Piece pieceMoved, int destinationCoordinate, Piece attackedPiece) {
            super(board, pieceMoved, destinationCoordinate, attackedPiece);
        }
    }

    public static class PawnJump extends Move {

        public PawnJump(Board board, Piece movedPiece, int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }

        @Override
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece:this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece :this.board.currentPlayer().getActivePieces()){
builder.setPiece(piece);
            }

            final Pawn movePawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movePawn);
            builder.setEnPassantPawn(movePawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

    }

    static abstract class CastleMove extends Move {

        public CastleMove(Board board, Piece movedPiece, int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }
    }

    public static class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(Board board, Piece movedPiece, int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }
    }

    public static class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(Board board, Piece movedPiece, int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }
    }

    public static class NullMove extends Move {

        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("can't execute null move");
        }
    }

    public static class MoveFactory {
        private MoveFactory() {
            throw new RuntimeException("Cannot make obj");
        }

        public static Move createMove(final Board board, final int currentCoordinate, final int destCoordinate) {
            for (final Move move : board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destCoordinate) {
                    return move;
                }

            }
            return NULL_MOVE;
        }
    }


}
