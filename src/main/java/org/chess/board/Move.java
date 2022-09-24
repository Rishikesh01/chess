package org.chess.board;

import org.chess.piece.Pawn;
import org.chess.piece.Piece;
import org.chess.piece.Rook;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Move {

    public static final Move NULL_MOVE = new NullMove();
    protected final boolean isFirstMove;
    final Board board;
    final Piece movedPiece;
    final int destCoordinate;

    public Move(Board board, Piece movedPiece, int destCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordinate = destCoordinate;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destCoordinate) {
        this.board = board;
        this.destCoordinate = destCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + this.destCoordinate;
        hash = 31 * hash + this.movedPiece.hashCode();
        hash = 31 * hash + this.movedPiece.getPiecePosition();
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

    public int getCurrentCoordinate() {
        return this.movedPiece.getPiecePosition();
    }

    private Board getBoard() {
        return this.board;
    }

    public static class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece pieceMoved,
                         final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object obj) {
            return this == obj || obj instanceof MajorMove && super.equals(obj);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardUtils.getPostionAtCoordinate(this.destCoordinate);
        }
    }

    public static class AttackMove extends Move {
        final Piece attackedPiece;

        public AttackMove(Board board, Piece piece, int destCoordinate, Piece attackedPiece) {
            super(board, piece, destCoordinate);
            this.attackedPiece = attackedPiece;
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

    public static class MajorAttackMove extends AttackMove {
        public MajorAttackMove(final Board board, final Piece piece, final int destCoordinate, final Piece pieceAttack) {
            super(board, piece, destCoordinate, pieceAttack);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType() + BoardUtils.getPostionAtCoordinate(this.destCoordinate);
        }
    }

    public static class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece pieceMoved,
                        final int destinationCoordinate) {
            super(board, pieceMoved, destinationCoordinate);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.getPostionAtCoordinate(this.destCoordinate);
        }
    }

    public static class PawnPromotion extends Move {
        final Move decoratedMove;
        final Pawn decoratedPawn;

        public PawnPromotion(final Move decoratedMove) {
            super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationCoordinate());
            this.decoratedMove = decoratedMove;
            this.decoratedPawn = (Pawn) decoratedMove.getMovedPiece();
        }

        @Override
        public int hashCode() {
            return decoratedMove.hashCode() + (31 * decoratedPawn.hashCode());
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnPromotion && super.equals(other);
        }

        @Override
        public Board execute() {
            final Board pawnMove = this.decoratedMove.execute();
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : pawnMove.currentPlayer().getActivePieces()) {
                if (!this.decoratedPawn.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece:pawnMove.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.decoratedPawn.getPromotionPiece().movePiece(this));
            builder.setMoveMaker(pawnMove.currentPlayer().getColor());
            return builder.build();
        }

        @Override
        public boolean isAttack() {
            return this.decoratedMove.isAttack();
        }

        @Override
        public Piece getAttackedPiece() {
            return this.decoratedMove.getAttackedPiece();
        }

        @Override
        public String toString() {
            return "";
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece pieceMoved,
                              final int destinationCoordinate,
                              final Piece attackedPiece) {
            super(board, pieceMoved, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.getPostionAtCoordinate(this.movedPiece.getPiecePosition()).charAt(0) + "x" +
                    BoardUtils.getPostionAtCoordinate(this.destCoordinate);
        }
    }

    public static class PawnPassEntAttackMove extends PawnAttackMove {

        public PawnPassEntAttackMove(Board board, Piece pieceMoved, int destinationCoordinate, Piece attackedPiece) {
            super(board, pieceMoved, destinationCoordinate, attackedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnPassEntAttackMove && super.equals(other);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                if (!piece.equals(this.getAttackedPiece())) {
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }
    }

    public static class PawnJump extends Move {

        public PawnJump(Board board, Piece movedPiece, int destCoordinate) {
            super(board, movedPiece, destCoordinate);
        }

        @Override
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

            final Pawn movePawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movePawn);
            builder.setEnPassantPawn(movePawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardUtils.getPostionAtCoordinate(this.destCoordinate);
        }

    }

    static abstract class CastleMove extends Move {
        protected final Rook castleRook;
        protected final int castleRookStartPos;
        protected final int castleRookDes;

        public CastleMove(Board board, Piece movedPiece,
                          int destCoordinate,
                          Rook castleRook,
                          int castleRookStartPos,
                          int castleRookDes) {
            super(board, movedPiece, destCoordinate);
            this.castleRook = castleRook;
            this.castleRookStartPos = castleRookStartPos;
            this.castleRookDes = castleRookDes;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDes,
                    this.castleRook.getPieceColor()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + this.castleRook.hashCode();
            result = 31 * result + this.castleRookDes;
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CastleMove)) {
                return false;
            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }
    }

    public static class KingSideCastleMove extends CastleMove {
        public KingSideCastleMove(Board board,
                                  Piece movedPiece,
                                  int destCoordinate,
                                  Rook castleRook,
                                  int castleRookStartPos,
                                  int castleRookDes) {
            super(board, movedPiece, destCoordinate, castleRook, castleRookStartPos, castleRookDes);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof KingSideCastleMove && super.equals(other);
        }
    }

    public static class QueenSideCastleMove extends CastleMove {
        public QueenSideCastleMove(Board board,
                                   Piece movedPiece,
                                   int destCoordinate, Rook castleRook,
                                   int castleRookStartPos,
                                   int castleRookDes) {
            super(board, movedPiece, destCoordinate, castleRook, castleRookStartPos, castleRookDes);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof QueenSideCastleMove && super.equals(other);
        }
    }

    public static class NullMove extends Move {

        public NullMove() {
            super(null, 65);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("can't execute null move");
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
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
