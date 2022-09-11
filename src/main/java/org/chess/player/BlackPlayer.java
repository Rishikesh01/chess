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
public class BlackPlayer extends Player {


    public BlackPlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
        super(board,whiteLegalMoves,blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
   return this.board.getBlackPieces();
    }

    @Override
    public Color getColor() {
        return Color.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
