package org.chess.board;

import org.chess.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Tile {
    private static final Map<Integer, EmptyTile> EMPTY_TILE_MAP = createAllEmptyTiles();
    protected final int coordinate;

    Tile(int coordinate) {
        this.coordinate = coordinate;
    }

    private static Map<Integer, EmptyTile> createAllEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return emptyTileMap;
    }

    public abstract boolean isTileEmpty();

    public abstract Piece getPiece();

}
