package org.chess.player;

import com.google.common.collect.ImmutableList;
import org.chess.Color;
import org.chess.board.Board;
import org.chess.board.Move;
import org.chess.board.Tile;
import org.chess.piece.Piece;
import org.chess.piece.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (this.board.getTile(61).isTileEmpty() && this.board.getTile(62).isTileEmpty()) {
                final Tile rookTile = this.board.getTile(62);
                if (!rookTile.isTileEmpty() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttackOnTile(61, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttackOnTile(21, opponentsLegalMoves).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(
                                this.board,
                                this.playerKing,
                                62,
                                (Rook) rookTile.getPiece(),
                                rookTile.getTitleCoordinate(),
                                61));
                    }
                }
            }
            if (this.board.getTile(59).isTileEmpty()
                    && this.board.getTile(58).isTileEmpty()
                    && this.board.getTile(57).isTileEmpty()) {
                final Tile rookTile = this.board.getTile(56);
                if (!rookTile.isTileEmpty() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new Move.QueenSideCastleMove(
                            this.board,
                            this.playerKing,
                            58,
                            (Rook) rookTile.getPiece(),
                            rookTile.getTitleCoordinate(),
                            59
                    ));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
