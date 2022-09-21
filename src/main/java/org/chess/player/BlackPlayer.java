package org.chess.player;

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
public class BlackPlayer extends Player {


    public BlackPlayer(Board board, Collection<Move> whiteLegalMoves, Collection<Move> blackLegalMoves) {
        super(board, blackLegalMoves,whiteLegalMoves);
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

    @Override
    public Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && this.isInCheck()) {
            if (this.board.getTile(5).isTileEmpty() && this.board.getTile(6).isTileEmpty()) {
                final Tile rookTile = this.board.getTile(7);
                if (!rookTile.isTileEmpty() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttackOnTile(5, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttackOnTile(6, opponentsLegalMoves).isEmpty() && rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(
                                new Move.KingSideCastleMove(
                                        this.board,
                                        this.playerKing,
                                        6,
                                        (Rook) rookTile.getPiece(),
                                        rookTile.getTitleCoordinate(),
                                        5)
                        );
                    }
                }
            }
            if (this.board.getTile(1).isTileEmpty()
                    && this.board.getTile(2).isTileEmpty()
                    && this.board.getTile(3).isTileEmpty()) {
                final Tile rookTile = this.board.getTile(0);
                if (!rookTile.isTileEmpty() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new Move.QueenSideCastleMove(
                            this.board,
                            this.playerKing,
                            2,
                            (Rook) rookTile.getPiece(),
                            rookTile.getTitleCoordinate(),
                            3
                    ));
                }
            }
        }
        return kingCastles;
    }
}
