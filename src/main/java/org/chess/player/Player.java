package org.chess.player;

import com.google.common.collect.ImmutableList;
import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.Move;
import org.chess.piece.King;
import org.chess.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    private final boolean isInCheck;


    protected Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentLegalMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.calculateAttackOnTile(this.playerKing.getPiecePosition(), opponentLegalMoves).isEmpty();
    }

    protected static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMove = new ArrayList<>();

        for (final Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMove.add(move);
            }
        }
        return ImmutableList.copyOf(attackMove);
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Invalid board");
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Color getColor();

    public abstract Player getOpponent();

    public abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentsLegalMoves);

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public boolean isInCheckMate() {
        return this.isInCheck && !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves() {
        for (final Move move : this.legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean isStaleMate() {
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    public King getPlayerKing() {
        return this.playerKing;
    }

    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL);
        }
        final Board transistionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttackOnTile(transistionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transistionBoard.currentPlayer().getLegalMoves());
        if (!kingAttacks.isEmpty())
            return new MoveTransition(this.board, move, MoveStatus.IN_CHECK);

        return new MoveTransition(transistionBoard, move, MoveStatus.DONE);
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

}
