package org.chess.player;

import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.Move;
import org.chess.piece.Piece;

import java.util.Collection;

/**
 * @author Rishikesh
 * @project chess
 */
public class WhitePlayer extends Player {
    public WhitePlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
