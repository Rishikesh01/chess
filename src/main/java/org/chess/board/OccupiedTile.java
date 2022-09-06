package org.chess.board;

import org.chess.Piece;

/**
 * @author Rishikesh
 * @project chess
 */
public final class OccupiedTile extends Tile {
    private final Piece piece;

    OccupiedTile(int coordinate, Piece piece) {
        super(coordinate);
        this.piece = piece;
    }

    @Override
    public boolean isTileEmpty() {
        return false;
    }

    @Override
    public Piece getPiece() {
        return this.piece;
    }
}
