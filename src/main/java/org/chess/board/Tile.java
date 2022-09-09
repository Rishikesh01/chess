package org.chess.board;

import com.google.common.collect.ImmutableMap;
import org.chess.piece.Piece;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rishikesh
 * @project chess
 */
public abstract class Tile {
    private static final Map<Integer, EmptyTile> EMPTY_TILE_MAP = createAllEmptyTiles();
    protected final int coordinate;

    protected Tile(final int coordinate) {
        this.coordinate = coordinate;
    }

    private static Tile CreateTile(final int coordinate,final Piece piece){
        return piece!=null? new OccupiedTile(coordinate,piece) : EMPTY_TILE_MAP.get(coordinate);
    }
    private static Map<Integer, EmptyTile> createAllEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for (int i = 0; i < BoardUtils.BOARD_SIZE; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public abstract boolean isTileEmpty();

    public abstract Piece getPiece();

}
