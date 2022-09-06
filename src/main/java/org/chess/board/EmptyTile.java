package org.chess.board;

import org.chess.piece.Piece;

/**
 * @author Rishikesh
 * @project chess
 */
public final class EmptyTile extends Tile {

   EmptyTile(int coordinate) {
        super(coordinate);
    }

    @Override
    public boolean isTileEmpty() {
        return true;
    }

    @Override
    public Piece getPiece() {
        return null;
    }
}
